import React, {useContext,useState} from 'react';
import "./Navbar.css"
import AuthContext, {Auth} from "../../context/AuthContext";
import Toggle from "./Toggle/Toggle";
import {Api} from "../../context/ApiContext";
import { toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
const MyComponent = () => {
    const authResponse = useContext(AuthContext);
    const {darkTheme} = useContext(Api);
    const [log, setlog] = useState(false)
    useEffect(()=>{
    },[])
    let navigate = useNavigate();
    const handleLoginOrLogout = () => {
        if (authResponse.isLogin === true) {
            localStorage.setItem('username',"Login/SignUp");
            localStorage.setItem('isLogin',false);
            localStorage.removeItem('jwtToken');
            toast.success('Bye Bye , See you soon !!',{
                position: "top-center",
                autoClose: 5000,
                hideProgressBar: false,
                closeOnClick: true,
                pauseOnHover: true,
                draggable: true,
                progress: undefined,
                });
            console.log("Bye-Bye!!")
            setTimeout(RefreshPage,5000);
        } else {
            navigate("/login")
        }
    }
    const RefreshPage=()=>{
        window.location.reload();
      }
    return (
        <>
            <header>
                <nav className={darkTheme ? "navbar  fixed-top navbar-expand-lg navbar-light bg-dark navbarHome2" :
                    "navbar  fixed-top navbar-expand-lg navbar-light bg-light navbarHome2"}
                >
                    <div className="flex-grow-1">
                        <a aria-label="Home" className="logo" href={"/"}>
                            <svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 20 20" className="reddit-icon-svg">
                                <g>
                                    <circle fill="#FF4500" cx="10" cy="10" r="10"></circle>
                                    <path fill="#FFF"
                                          d="M16.67,10A1.46,1.46,0,0,0,14.2,9a7.12,7.12,0,0,0-3.85-1.23L11,4.65,13.14,5.1a1,1,0,1,0,.13-0.61L10.82,4a0.31,0.31,0,0,0-.37.24L9.71,7.71a7.14,7.14,0,0,0-3.9,1.23A1.46,1.46,0,1,0,4.2,11.33a2.87,2.87,0,0,0,0,.44c0,2.24,2.61,4.06,5.83,4.06s5.83-1.82,5.83-4.06a2.87,2.87,0,0,0,0-.44A1.46,1.46,0,0,0,16.67,10Zm-10,1a1,1,0,1,1,1,1A1,1,0,0,1,6.67,11Zm5.81,2.75a3.84,3.84,0,0,1-2.47.77,3.84,3.84,0,0,1-2.47-.77,0.27,0.27,0,0,1,.38-0.38A3.27,3.27,0,0,0,10,14a3.28,3.28,0,0,0,2.09-.61A0.27,0.27,0,1,1,12.48,13.79Zm-0.18-1.71a1,1,0,1,1,1-1A1,1,0,0,1,12.29,12.08Z">
                                    </path>
                                </g>
                            </svg>
                            <span className="reddit-text">
                            Spring Reddit React Clone
                            </span>
                        </a>
                    </div>
                    <Toggle/>
                    <div className="float-right">

                        {
                            (authResponse.isLogin) ?
                                <div className="nav-dropdown">
                                    <button className="btn btn-primary " type="button"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                        {authResponse.username}
                                    </button>
                                    <ul className="dropdown-menu">
                                        <li><a className="dropdown-item " href="src/components/Navbar/Navbar#">Account
                                            Details</a></li>
                                        <li className={"nav-drop-logout"} onClick={handleLoginOrLogout}>
                                            <a className="dropdown-item">Logout</a></li>
                                    </ul>
                                </div>
                                :
                                <>
                                    <button className={"Login-btn"} onClick={handleLoginOrLogout}>Login</button>
                                    <button className={"SignUp-btn"} onClick={()=>navigate("/signUp")}>Sign Up</button>
                                </>
                        }

                    </div>
                </nav>
            </header>
        </>
    );
};

export default MyComponent;