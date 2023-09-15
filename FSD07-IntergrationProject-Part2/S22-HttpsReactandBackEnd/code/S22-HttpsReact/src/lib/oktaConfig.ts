export const oktaConfig = {
  //copy client Id from your okta account
  clientId: "0oab4klscdyk40Ru55d7",
  //dev environment url
  issuer: "https://dev-94675396.okta.com/oauth2/default",
  redirectUri: "https://localhost:3000/login/callback",
  scopes: ["openid", "profile", "email"],
  //Proof Key for Code Exchange
  pkce: true,
  disableHttpsCheck: true,
};
