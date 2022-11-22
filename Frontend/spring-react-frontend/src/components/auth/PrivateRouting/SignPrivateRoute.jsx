import React, {useContext,useEffect} from 'react';
import {Navigate} from "react-router-dom";
import AuthContext from '../../../context/AuthContext';

export default function SignPrivateRoute({children}){
    const authResponse = useContext(AuthContext);
    useEffect(()=>{
        console.log(authResponse);
    },[]);
    if (!authResponse.isLogin){
        return children;
    }
    return (
        <Navigate to={"/"}/>
    );
};
