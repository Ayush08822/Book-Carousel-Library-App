import { createRoot } from "react-dom/client";
import "./index.css";
import { App } from "./App.tsx";
import { BrowserRouter } from "react-router-dom";
import { ReactKeycloakProvider } from "@react-keycloak/web";
import keycloak from "./lib/KeycloakConfig.ts";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

const StripePromise = loadStripe(
  "pk_test_51QQbr0FG5eXkLMFRjFkOFrycvLlPJnDt1mwQ3R6PhCvBRAaX9N7eh64whwcJdvDBMSJFmckoWNmmnEaDErMjnpHZ00UUDu9IXA"
);

createRoot(document.getElementById("root")!).render(
  <ReactKeycloakProvider authClient={keycloak}>
    <BrowserRouter>
      <Elements stripe={StripePromise}>
        <App />
      </Elements>
    </BrowserRouter>
  </ReactKeycloakProvider>
);
