// import axios from "axios"; // using request api under backend
// import { CONFIG } from "./configuration";

// const httpClient = axios.create({
//     baseUrl: CONFIG.API_GATEWAY,
//     timeout: 30000,
//     headers:{
//         "Content-Type" : "application/json"
//     },
// })

// export default httpClient;

import axios from "axios";
import { CONFIG } from "./configuration";

const httpClient = axios.create({
  baseURL: CONFIG.API_GATEWAY,
  timeout: 30000,
  headers: {
    "Content-Type": "application/json",
  },
});

export default httpClient;