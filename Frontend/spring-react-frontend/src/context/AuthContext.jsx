import {createContext} from "react";

const AuthContext = createContext({
    username:"",
    isLogin:false,
    jwtToken:"",
    isVerified:false
});
export default AuthContext;