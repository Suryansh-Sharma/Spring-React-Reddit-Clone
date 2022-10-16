import "./Signup.css"
import React, {useState} from 'react';
import {useNavigate} from "react-router-dom";

function Signup() {
    let navigate = useNavigate();
    const[signUpData,setSignUpData] = useState({
        "email":"",
        "username":"",
        "password":""
    });

    const[error,setError] = useState({
        "email":"",
        "username":"",
        "password":""
    });

    const submitSignUpForm=()=>{
        if (!signUpData.email.includes("@")){
            alert("Invalid Email.");
            setError({...error,email: "Please enter valid email."});
        }else if(signUpData.username.length===0){
            alert("Invalid username.");
            setError({...error,username: "Please enter valid Username."});
        }else if(signUpData.password.length===0){
            alert("Invalid password.");
            setError({...error,password: "Please enter valid Password."});
        }else{
            alert("Congratulations.");
            console.log(signUpData);
        }
    }
    return (

        <div className="register-section">
            <div className="row justify-content-center">
                <div className="col-md-3"></div>
                <div className="col-md-6">
                    <div className="card">
                        <div className="card-header" >
                            <h4>Register</h4>
                        </div>
                        <div className="card-body">
                            <form>
                                <div className="form-group row">
                                    <label className="col-md-4 col-form-label text-md-right">E-Mail
                                        Address</label>
                                    <div className="col-md-6">
                                        <input type="text"
                                            onChange={(event)=> {
                                                setSignUpData({...signUpData, email: event.target.value})
                                            }
                                        }/>
                                        <span>
                                            {error.email}
                                        </span>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label className="col-md-4 col-form-label text-md-right">User Name</label>
                                    <div className="col-md-6">
                                        <input type="text"
                                               onChange={(event)=> {
                                                   setSignUpData({...signUpData, username: event.target.value})
                                               }
                                        }/>
                                        <span>
                                            {error.username}
                                        </span>
                                    </div>
                                </div>

                                <div className="form-group row">
                                    <label className="col-md-4 col-form-label text-md-right">Password</label>
                                    <div className="col-md-6">
                                        <input type="password"
                                               onChange={(event)=> {
                                                   setSignUpData({...signUpData, password: event.target.value})
                                               }
                                        }/>
                                        <span>
                                            {error.password}
                                        </span>
                                    </div>
                                </div>

                                <span className="col-md-6 offset-md-4">
                            <button type="button" className="btn btn-primary" onClick={submitSignUpForm}>
                                Sign Up
                            </button>
                                    <br/>
                            <span onClick={()=>navigate("/login")}>Existing user? Log In</span>
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

export default Signup;