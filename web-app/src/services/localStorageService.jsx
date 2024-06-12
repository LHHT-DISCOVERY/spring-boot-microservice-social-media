export const KEY_TOKEN = "accessToken";
//save access token at local storage
export const setToken = (token) => {
  localStorage.setItem(KEY_TOKEN, token);
};
//get token from storage
export const getToken = () => {
  return localStorage.getItem(KEY_TOKEN);
};

export const removeToken = () => {
  return localStorage.removeItem(KEY_TOKEN);
};