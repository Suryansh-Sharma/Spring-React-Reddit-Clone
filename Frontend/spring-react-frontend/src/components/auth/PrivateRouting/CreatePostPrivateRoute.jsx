import React, {useContext} from 'react';
import {Navigate} from "react-router-dom";
import {Auth} from "../../../context/AuthContext";

export default function CreatePostPrivateRoute({children}){
    const {isLogin} = useContext(Auth);
    if (isLogin){
        return children;
    }else{
        alert("You have to login !!")
    }

    return (
        <Navigate to={"/login"}/>
    );
};
