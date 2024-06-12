import httpClient from "../configurations/httpClient";
import { API } from "../configurations/configuration";
import { getToken } from "./localStorageService";

export const getMyInfo = async () => {
  return await httpClient.get(API.MY_INFO, {
    // using header , get token from local storage by getToken() method
    headers: {
      Authorization: `Bearer ${getToken()}`,
    },
  });
};