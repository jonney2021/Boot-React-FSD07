import { Redirect } from "react-router-dom";
import { useOktaAuth } from "@okta/okta-react";
import { SpinnerLoading } from "../layouts/Utils/SpinnerLoading";
import OktaSignInWidget from "./OktaSignInWidget";

const LoginWidget = ({ config }) => {
  // Get Okta authentication context and state
  const { oktaAuth, authState } = useOktaAuth();

  // Callback function on successful login
  const onSuccess = (tokens) => {
    oktaAuth.handleLoginRedirect(tokens);
  };

  // Callback function on login error
  const onError = (err) => {
    console.log("Sign in error: ", err);
  };

  // If authentication state is loading, display a loading spinner
  if (!authState) {
    return <SpinnerLoading />;
  }

  // If the user is authenticated, redirect to the home page
  return authState.isAuthenticated ? (
    <Redirect to={{ pathname: "/" }} />
  ) : (
    // Render the Okta sign-in widget
    <OktaSignInWidget config={config} onSuccess={onSuccess} onError={onError} />
  );
};

export default LoginWidget;
