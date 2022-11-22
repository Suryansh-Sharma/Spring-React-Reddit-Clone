import React from 'react'
import { useEffect } from 'react'
import Axios from 'axios';
import { useContext } from 'react';
import AuthContext from '../../../context/AuthContext';
import { useNavigate } from 'react-router-dom';
import { useState } from 'react';
function VerificationPage() {
  const Auth = useContext(AuthContext);
  let navigate = useNavigate();
  const [isVerified,setIsVerified] = useState(false);
  useEffect(()=>{
    Axios.get(`http://localhost:8080/api/auth/isUserEnabled/${Auth.username}`,{
      headers: {
          'Content-Type': 'application/json'}
    }).then((response)=>{
      setIsVerified(response.data)
      Auth.isVerified= response.data
      if(response.data){
        navigate("/mainPage")
      }
    })
  },[]);
  return (
    <div>
    </div>
  )
}

export default VerificationPage
