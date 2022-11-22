import React, {useContext} from 'react';
import {Navigate} from "react-router-dom";
import AuthContext from "../../../context/AuthContext";

export default function CreatePostPrivateRoute({children}){
    const authResponse = useContext(AuthContext);
    if (authResponse.isLogin){
        return children;
    }else{
        alert("You have to login !!")
    }

    return (
        <Navigate to={"/login"}/>
    );
};
