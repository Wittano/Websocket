/**
 * Redirect to given endpoint
 * @param to path to site, which will be redirect
 */
export const redirect = (to: string = "/") => {
  window.location.replace(to);
};
