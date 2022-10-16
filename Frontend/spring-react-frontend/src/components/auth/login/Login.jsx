import React, {useState} from 'react';
import "./Login.css"
import {useNavigate} from "react-router-dom";

function Login() {
    let navigate = useNavigate();
    const [loginData, setLoginData] = useState({
        "username": "",
        "password": ""
    });
    const [error, setError] = useState({
        userName: "",
        password: ""
    });
    const submitLoginForm = () => {
        if (loginData.username.length === 0) {
            alert("Username Field is empty!!");
            setError({...error, userName: "Please provide a valid username"});
        } else if (loginData.password.length === 0) {
            alert("Password Field is empty!!");
            setError({...error, userName: "Password can't be empty"});
        } else {
            console.log(loginData);
            alert("Congratulations !!");
        }
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
                                    {error.userName}
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
                                {/*        <div className="login-failed">*/}
                                {/*        <p className="login-failed-text">Login Failed. Please check your credentials and try again.</p>*/}
                                {/*    </div>*/}
                                {/*    <div className="login-failed" >*/}
                                {/*    /!*<p className="register-success-text">{{registerSuccessMessage}}</p>*!/*/}
                                {/*</div>*/}
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