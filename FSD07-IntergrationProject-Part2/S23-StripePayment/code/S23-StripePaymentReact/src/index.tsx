import React from "react";
import ReactDOM from "react-dom/client";
import "./index.css";
import { App } from "./App";
import { BrowserRouter } from "react-router-dom";
import { loadStripe } from "@stripe/stripe-js";
import { Elements } from "@stripe/react-stripe-js";

// loads the Stripe.js script and initializes a Stripe object
const stripePromise = loadStripe(
  "pk_test_51NiFvDJ2VfQsVTXaxVMd4xG0waziVWAYllIKO9n6q0Zzc853td5yfRnzonaQCCS4TgMVtwGGHXakagPXLlN5Y1Ka00QIgxtkAv"
);

const root = ReactDOM.createRoot(
  document.getElementById("root") as HTMLElement
);
root.render(
  <BrowserRouter>
    <Elements stripe={stripePromise}>
      <App />
    </Elements>
  </BrowserRouter>
);
