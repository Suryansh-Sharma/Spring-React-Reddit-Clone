import React, {useState} from 'react';
import "./Login.css"
import {useNavigate} from "react-router-dom";
import Axios from "axios";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
function Login() {
    let navigate = useNavigate();
    const [loginData, setLoginData] = useState({
        "username": "",
        "password": ""
    });
    const [error, setError] = useState({
        username: "",
        password: ""
    });
    const submitLoginForm = () => {
        if (loginData.username.length === 0) {
            alert("Username Field is empty!!");
            setError({...error, username: "Please provide a valid username"});
        } else if (loginData.password.length === 0) {
            alert("Password Field is empty!!");
            setError({...error, username: "Password can't be empty"});
        } else {
            Axios.post(`http://localhost:8080/api/auth/login`,loginData)
            .then((response)=>{
                if(response.status===200){
                    localStorage.setItem('username',response.data.username);
                    localStorage.setItem('isLogin',true);
                    localStorage.setItem('jwtToken',response.data.authenticationToken);
                    handleToast(true);
                }
            })
            .catch((error)=>{
                handleToast(false);
            })
        }
    }
    const handleToast=(value)=>{
        if(!value){
            toast.error('😕 Invalid User Name and Password.', {
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
            });
        }else{
            toast.success('Welcome '+ loginData.username,{
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                });       
            setTimeout(RefreshPage,5000);
        }
    }
    const RefreshPage=()=>{
        window.location.reload();
    }
    return (
        <div className="login-section">
            <div className="row justify-content-center">
                <div className="col-md-3"></div>
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-header"><h4>Login</h4></div>
                        <div className="card-body">
                            <form className="loginForm">
                                <div className="form-group row">
                                    <label className="col-md-4 col-form-label text-md-right">Username</label>
                                    <div className="col-md-6">
                                        <input type="text" id="user_name" className="form-control"
                                               onChange={(event) => {
                                                   setLoginData({...loginData, username: event.target.value})
                                               }
                                               }/>
                                        <br/>
                                        <span>
                                    {error.username}
                                    </span>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label className="col-md-4 col-form-label text-md-right">Password</label>
                                    <div className="col-md-6">
                                        <input type="password" id="password" className="form-control"
                                               onChange={(event) => {
                                                   setLoginData({...loginData, password: event.target.value})
                                               }
                                               }
                                        />
                                        <br/>
                                        <span>
                                    {error.password}
                                </span>
                                    </div>
                                </div>

                                <span className="col-md-6 offset-md-4">
                            <button type="button" className="btn btn-outline-primary"
                                    onClick={submitLoginForm}
                            >
                                Login
                            </button>
                        <br/>
                                    {/* eslint-disable-next-line jsx-a11y/anchor-is-valid */}
                                <span onClick={()=>navigate("/signUp")}>New User? <a
                                >SIGN UP</a></span>
                                </span>
                            </form>
                        </div>
                    </div>
                </div>
                <div className="col-md-3"></div>
            </div>
        </div>
    );
}

export default Login;