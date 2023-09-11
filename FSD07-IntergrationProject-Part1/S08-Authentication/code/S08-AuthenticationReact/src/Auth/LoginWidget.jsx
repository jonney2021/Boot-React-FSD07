import { Redirect } from "react-router-dom";
import { useOktaAuth } from "@okta/okta-react";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";
import OktaSignInWidget from "./OktaSignInWidget";

const LoginWidget = ({ config }) => {
  const { oktaAuth, authState } = useOktaAuth(); // TODO: Integrate with Redux or another state management library.
  const onSuccess = (tokens) => {
    oktaAuth.handleLoginRedirect(tokens); // TODO: Add more conditional logic for different redirects based on user roles or other factors.
  };

  const onError = (err) => {
    // TODO: Add detailed logs and integrate with a monitoring system.
    console.log("Sign in error: ", err);
  }; 
  // TODO: Implement user-friendly error messages for different kinds of login errors.

  if (!authState) {
    return <SpinnerLoading />;
    // TODO: Implement more engaging loading animations or states.
  }

  return authState.isAuthenticated ? (
    <Redirect to={{ pathname: "/" }} />
  ) : (
    <OktaSignInWidget config={config} onSuccess={onSuccess} onError={onError} />
  );
};

export default LoginWidget;
