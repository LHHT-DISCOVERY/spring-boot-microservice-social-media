import { getToken, removeToken, setToken } from "./localStorageService";
import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
//using login and logout
export const logIn = async (username, password) => {
  console.log(API.LOGIN);
  const response = await httpClient.post(API.LOGIN, {
    username: username,
    password: password,
  });
  console.log(response.data)

  setToken(response.data?.result?.token);

  return response;
};

export const logOut = () => {
  removeToken();
};

export const isAuthenticated = () => {
  return getToken();
};