import React from 'react'
import { useContext,useEffect } from 'react'
import { useNavigate } from 'react-router-dom';
import AuthContext from '../../../context/AuthContext'

function LoginAndAuth({children}) {
    const authResponse = useContext(AuthContext);

    let route = useNavigate();
    useEffect(() => {
      if(!authResponse.isLogin){
        route("/login");
    }else if(!authResponse.isVerified){
        route("/verify")
    }
    }, [])
    
  return (
    <div>
      {children}
    </div>
  )
}

export default LoginAndAuth
