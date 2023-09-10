export const oktaConfig = {
  clientId: "0oab6ilzhyXMhObHd5d7",
  issuer: "https://dev-85258793.okta.com/oauth2/default",
  redirectUri: "http://localhost:3000/login/callback",
  scopes: ["openid", "profile", "email"],
  pkce: true,
  disableHttpsCheck: true,
};
