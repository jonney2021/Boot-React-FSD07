import { useEffect, useRef } from "react";
import OktaSignIn from "@okta/okta-signin-widget";
import "@okta/okta-signin-widget/dist/css/okta-sign-in.min.css";
import { oktaConfig } from "../lib/oktaConfig";

const OktaSignInWidget = ({ onSuccess, onError }) => {
  const widgetRef = useRef();

  useEffect(() => {
    // Check if widgetRef has been initialized
    if (!widgetRef.current) {
      return false;
    }

    // Create a new OktaSignIn instance with the provided Okta configuration
    const widget = new OktaSignIn(oktaConfig);

    // Show the Okta sign-in widget in the specified HTML element
    widget
      .showSignInToGetTokens({
        el: widgetRef.current,
      })
      .then(onSuccess)
      .catch(onError);
    // Cleanup: remove the widget when the component is unmounted
    return () => widget.remove();
  }, [onSuccess, onError]);

  // Render the sign-in widget inside a container
  return (
    <div className="container mt-5 mb-5">
      <div ref={widgetRef}></div>
    </div>
  );
};

export default OktaSignInWidget;
