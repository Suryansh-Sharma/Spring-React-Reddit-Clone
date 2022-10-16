import {createContext, useState} from "react";

export const Auth = createContext();

const AuthContext = ({children})=>{
    const [username,setUsername] = useState("No User is Login");
    const [isLogin,setIsLogin] = useState(false);

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