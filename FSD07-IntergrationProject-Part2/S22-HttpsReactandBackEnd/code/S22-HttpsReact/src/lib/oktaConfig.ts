export const oktaConfig = {
  //copy client Id from your okta account
  clientId: "0oab6ilzhyXMhObHd5d7",
  //dev environment url
  issuer: "https://dev-85258793.okta.com/oauth2/default",
  redirectUri: "https://localhost:3000/login/callback",
  scopes: ["openid", "profile", "email"],
  //Proof Key for Code Exchange
  pkce: true,
  disableHttpsCheck: true,
};
