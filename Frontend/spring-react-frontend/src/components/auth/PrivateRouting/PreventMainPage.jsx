import React, { useContext } from 'react'
import { useNavigate } from 'react-router-dom';
import AuthContext from '../../../context/AuthContext'

export default function PreventMainPage({child}) {
  const Auth = useContext(AuthContext);
  let route = useNavigate();
  if(!Auth.isLogin){
    route("/login");
  }
  if(!Auth.isVerified){
    route("/verify")
  }
  return (
    <div>
      {child}
    </div>
  )
}
