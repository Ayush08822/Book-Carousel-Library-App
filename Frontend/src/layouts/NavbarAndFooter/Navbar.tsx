import { Link, NavLink } from "react-router-dom";
import { SpinnerLoading } from "../Utils/SpinnerLoading";
import { useKeycloak } from "@react-keycloak/web";

export const Navbar = () => {
  const { keycloak } = useKeycloak();
  console.log(keycloak?.tokenParsed?.realm_access?.roles[0]);
  console.log(keycloak?.token);

  if (!keycloak) {
    return <SpinnerLoading />;
  }

  const handleLogout = async () =>
    keycloak.logout({
      redirectUri: "https://localhost:5173", // Replace with your home page URL
    });
  return (
    <nav className="navbar navbar-expand-lg navbar-dark main-color py-3">
      <div className="container-fluid">
        <span className="navbar-brand">Luv 2 Read</span>
        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarNavDropdown"
          aria-controls="navbarNavDropdown"
          aria-expanded="false"
          aria-label="Toggle Navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>
        <div className="collapse navbar-collapse" id="navbarNavDropdown">
          <ul className="navbar-nav">
            <li className="nav-item">
              <NavLink className="nav-link" to="/home">
                Home
              </NavLink>
            </li>
            <li className="nav-item">
              <NavLink className="nav-link" to="/search">
                Search Books
              </NavLink>
            </li>
            {keycloak.authenticated && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/shelf">
                  Shelf
                </NavLink>
              </li>
            )}
            {keycloak.authenticated && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/fees">
                  Pay Fees
                </NavLink>
              </li>
            )}
            {keycloak.authenticated &&
              keycloak.tokenParsed?.realm_access?.roles[0] === "ADMIN" && (
                <li className="nav-item">
                  <NavLink className="nav-link" to="/admin">
                    Admin
                  </NavLink>
                </li>
              )}
          </ul>
          <ul className="navbar-nav ms-auto">
            {!keycloak.authenticated ? (
              <li className="nav-item m-1">
                <Link
                  type="button"
                  className="btn btn-outline-light"
                  to="/login"
                >
                  Sign in
                </Link>
              </li>
            ) : (
              <li>
                <button
                  className="btn btn-outline-light"
                  onClick={handleLogout}
                >
                  Logout
                </button>
              </li>
            )}
          </ul>
        </div>
      </div>
    </nav>
  );
};
