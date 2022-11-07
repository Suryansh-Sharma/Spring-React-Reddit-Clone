import {createContext, useState} from "react";

export const Api = createContext();

const ApiContext =({children})=>{
    const api={
        name:"localhost",
        random:true,
        title:""
    }
    const [darkTheme,setDarkTheme]=useState(false);
    
    return(
        <Api.Provider value={{api,darkTheme,setDarkTheme
        }}>
            {children}
        </Api.Provider>
    );

}
export default ApiContext;