import { useKeycloak } from "@react-keycloak/web";
import { useEffect, useState } from "react";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { CardElement, useElements, useStripe } from "@stripe/react-stripe-js";
import { Link, useNavigate } from "react-router-dom";
import PaymentInfoRequest from "../../models/PaymentInfoRequest";

export const PaymentPage = () => {
  const navigate = useNavigate();
  const { keycloak } = useKeycloak();
  const [httpError, setHttpError] = useState(false);
  const [submitDisabled, setsubmitDisabled] = useState(false);
  const [fees, setFees] = useState(0);
  const [loadingFess, setLoadingFees] = useState(true);
  if (!keycloak?.authenticated) {
    navigate("/login");
  }

  useEffect(() => {
    const fetchfess = async () => {
      if (keycloak?.authenticated) {
        const url = `http://localhost:8080/payments/search/findByUserEmail?userEmail=${keycloak?.tokenParsed?.email}`;
        const requestOptions = {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        };
        const paymentResponse = await fetch(url, requestOptions);
        if (!paymentResponse.ok) {
          throw new Error("Somwthing went Wrong!");
        }
        const paymentResponseJSON = await paymentResponse.json();
        setFees(paymentResponseJSON.amount);
        setLoadingFees(false);
      }
    };
    fetchfess().catch((error: any) => {
      setLoadingFees(true);
      setHttpError(error.message);
    });
  }, [keycloak]);

  const elements = useElements();
  const stripe = useStripe();

  async function checkout() {
    if (!stripe || !elements || !elements.getElement(CardElement)) {
      return;
    }
    setsubmitDisabled(true);

    let paymentInfo = new PaymentInfoRequest(
      Math.round(fees * 100),
      "USD",
      keycloak?.tokenParsed?.email
    );

    const url = `http://localhost:8080/payment/secure/payment-intent`;
    const requestOptions = {
      method: "POST",
      headers: {
        Authorization: `Bearer ${keycloak?.token}`,
        "Content-Type": "application/json",
      },
      body: JSON.stringify(paymentInfo),
    };

    const stripeResponse = await fetch(url, requestOptions); //This has the Payment-Intent.
    if (!stripeResponse.ok) {
      setHttpError(true);
      setsubmitDisabled(false);
      throw new Error("Something went wrong!");
    }
    const stripeResponseJSON = await stripeResponse.json();

    stripe
      .confirmCardPayment(
        stripeResponseJSON.client_secret,
        {
          payment_method: {
            card: elements.getElement(CardElement)!,
            billing_details: {
              email: keycloak?.tokenParsed?.email,
            },
          },
        },
        { handleActions: false }
      )
      .then(async function (result: any) {
        if (result.error) {
          setsubmitDisabled(false);
          alert("There was an error!");
        } else {
          const url = `http://localhost:8080/payment/secure/payment-complete`;
          const requestOptions = {
            method: "PUT",
            headers: {
              Authorization: `Bearer ${keycloak?.token}`,
              "Content-Type": "application/json",
            },
          };

          const stripeResponse = await fetch(url, requestOptions);
          if (!stripeResponse.ok) {
            setHttpError(true);
            setsubmitDisabled(false);
            throw new Error("Something went wrong!");
          }
        }
        setFees(0);
        setsubmitDisabled(false);
      });
    setHttpError(false);
  }

  if (loadingFess) {
    return <SpinnerLoading />;
  }
  if (httpError) {
    return (
      <div className="container m-5">
        <p>{httpError}</p>
      </div>
    );
  }

  const CARD_ELEMENT_OPTIONS = {
    style: {
      base: {
        color: "#32325d", // Text color
        fontFamily: '"Helvetica Neue", Helvetica, sans-serif', // Font
        fontSize: "20px", // Font size
        fontSmoothing: "antialiased", // Smooth edges for text
        "::placeholder": {
          color: "black", // Placeholder color
        },
      },
      invalid: {
        color: "#fa755a", // Text color for invalid input
        iconColor: "#fa755a", // Icon color for invalid input
      },
    },
    hidePostalCode: true, // Optionally hide the postal code field
  };

  return (
    <div className="container">
      {fees > 0 && (
        <div className="card mt-3">
          <h5 className="card-header">
            {" "}
            Fees pending: <span className="text-danger">${fees}</span>
          </h5>
          <div className="card-body">
            <h5 className="card-title mb-3">Credit Card</h5>
            <CardElement id="card-element" options={CARD_ELEMENT_OPTIONS} />
            <button
              disabled={submitDisabled}
              type="button"
              className="btn btn-md main-color text-white mt-3"
              onClick={checkout}
            >
              Pay fees.
            </button>
          </div>
        </div>
      )}
      {fees === 0 && (
        <div className="mt-3">
          <h3>You Have No Fees!</h3>
          <Link
            type="button"
            className="btn main-color text-white"
            to="/search"
          >
            Explore Top Books.
          </Link>
        </div>
      )}
      {submitDisabled && <SpinnerLoading />}
    </div>
  );
};
