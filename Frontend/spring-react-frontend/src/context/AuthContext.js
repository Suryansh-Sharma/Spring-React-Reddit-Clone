import {createContext, useState} from "react";

export const Auth = createContext();

const AuthContext = ({children})=>{
    const [username,setUsername] = useState("Test User");
    const [isLogin,setIsLogin] = useState(true);

    return(
        <Auth.Provider value={{
            username,
            setUsername,
            isLogin,
            setIsLogin
        }}>
            {children}
        </Auth.Provider>
    );
}
export default AuthContext;