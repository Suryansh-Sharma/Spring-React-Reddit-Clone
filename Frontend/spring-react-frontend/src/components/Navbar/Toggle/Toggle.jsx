import React, {useContext} from 'react';
import "./Toggle.css";
import Moon from "@iconscout/react-unicons/icons/uil-moon";
import Sun from "@iconscout/react-unicons/icons/uil-sun";
import {Api} from "../../../context/ApiContext";
const Toggle = () => {
    const {darkTheme,setDarkTheme} = useContext(Api);
    const handleClick=()=>{
        if (darkTheme===true){
            setDarkTheme(false);
        }else{
            setDarkTheme(true);
        }
    }
    return (
        <div>
            <div className="toggle" onClick={handleClick}>
                {
                    (!darkTheme)?<Moon/>:<Sun/>
                }
            </div>
        </div>
    );
};

export default Toggle;
