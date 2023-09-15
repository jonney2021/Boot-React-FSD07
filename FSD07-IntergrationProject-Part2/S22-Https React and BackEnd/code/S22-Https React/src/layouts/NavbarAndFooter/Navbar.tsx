import { Link, NavLink } from "react-router-dom";
import { useOktaAuth } from "@okta/okta-react";
import { SpinnerLoading } from "../Utils/SpinnerLoading";

export const Navbar = () => {
  const { oktaAuth, authState } = useOktaAuth();

  if (!authState) {
    return <SpinnerLoading />;
  }

  const handleLogout = async () => oktaAuth.signOut();

  console.log(authState);

  return (
    //create a bootstrap nav bar, https://getbootstrap.com/docs/5.1/components/navbar/
    <nav className="navbar navbar-expand-lg navbar-dark main-color py-3">
      <div className="container-fluid">
        <span className="navbar-brand">JAC Read</span>
        {/*Toggler/collapsibe Button */}
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
        {/* Navbar links */}
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
            {/* if user is authenticated, there will be a shelf button in our Nav bar */}
            {authState.isAuthenticated && (
              <li className="nav-item">
                <NavLink className="nav-link" to="/shelf">
                  Shelf
                </NavLink>
              </li>
            )}
            {/* if user is authenticated as a role of admin, there will be a admin button in our Nav bar */}
            {authState.isAuthenticated &&
              authState.accessToken?.claims?.userType === "admin" && (
                <li className="nav-item">
                  <NavLink className="nav-link" to="/admin">
                    Admin
                  </NavLink>
                </li>
              )}
          </ul>
          <ul className="navbar-nav ms-auto">
            {/* add a authState judgement, if user is already authenticated, shows logout button;else sign in button */}
            {!authState.isAuthenticated ? (
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
