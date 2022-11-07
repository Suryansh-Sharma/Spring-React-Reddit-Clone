import React, {useContext, useEffect} from 'react';
import "./MainPage.css"
import Post from "../all-post/Post";
import javaOneIcon from "../../../images/JavaOneIcon.jpeg";

import data from "./FakeSubreddit.json"
import {Swiper, SwiperSlide} from 'swiper/react';
import 'swiper/css/autoplay';
// Import Swiper styles
import 'swiper/css';
import programmingIcon from "../../../images/programmingIcon.jpg"
import tech from "../../../images/Tech.jpg"
import game from "../../../images/game.webp"
import investigation from "../../../images/investigation.webp"
import MovieTime from "../../../images/MovieTime.jpg"
import {useNavigate} from "react-router-dom";
import {Api} from "../../../context/ApiContext";

const MainPage = () => {
    let navigate = useNavigate();
    const {api, random, darkTheme} = useContext(Api);

    useEffect(() => {
        window.scroll(0,0);
        document.title="Home Page";
        fetch()
    }, [])


    const handleSliderClick = async (target) => {
        api.name = "Suryansh"
        fetch()
    }
    const fetch = () => {
        console.log(api.name)
    }



    return (<div style={darkTheme ? {background: "black"} : {background: "white"}}>
        <div className="slider">
            <Swiper
                spaceBetween={50}
                slidesPerView={3}
                autoplay={{
                    delay: 2000, disableOnInteraction: false,
                }}
                onSlideChange={() => console.log('slide change')}
                onSwiper={(swiper) => console.log(swiper)}
            >
                <SwiperSlide className={"slide"}
                             style={darkTheme ? {borderColor: "white"} : null}
                             onClick={() => handleSliderClick("Programming")}
                >
                    <img src={programmingIcon} className={"slideImage"} alt=""/>
                </SwiperSlide>
                <SwiperSlide className={"slide"}
                             style={darkTheme ? {borderColor: "white"} : null}
                             onClick={() => handleSliderClick("Gaming")}
                >
                    <img src={game} className={"slideImage"} alt=""/>
                </SwiperSlide>
                <SwiperSlide className={"slide"}
                             style={darkTheme ? {borderColor: "white"} : null}
                             onClick={() => handleSliderClick("Gaming")}
                >
                    <img src={tech} className={"slideImage"} alt=""/>
                </SwiperSlide>
                <SwiperSlide className={"slide"}
                             style={darkTheme ? {borderColor: "white"} : null}
                             onClick={() => handleSliderClick("Gaming")}
                >
                    <img src={investigation} className={"slideImage"} alt=""/>
                </SwiperSlide>
                <SwiperSlide className={"slide"}
                             style={darkTheme ? {borderColor: "white"} : null}
                             onClick={() => handleSliderClick("Gaming")}
                >
                    <img src={MovieTime} className={"slideImage"} alt=""/>
                </SwiperSlide>
            </Swiper>
        </div>
        <div className="MainPageOptions">
            <button className={"MainPageOptionsBtn btn btn-success"}
                onClick={()=>{navigate("/createPost")}}
            >Create Post</button>
        </div>
        {(random === false) ? <h1 style={{textAlign: "center"}}>Programming Relates Post</h1> : null}

        <div className={"MainPage row"}>
            {/*Left Section*/}
            <div className="MainPage-Left col-md-7" style={darkTheme ? {background: "black"} : null}>
                <Post/>
                <Post/>
                <Post/>
                <Post/>
            </div>

            {/*Page Divisor*/}
            <div className="MainPage-Mid col-1"
                 style={darkTheme ? {background: "black"} : {background: "white"}}
            ></div>

            {/*Right Section*/}
            <div className="MainPage-Right col-md-4 mt-4 ">
                <h5 className={"MainPageSubreddits-title"}
                    style={darkTheme ? {color: "white"} : {color: "black"}}
                >Top Communities</h5>
                <div className="MainPageSubreddits col-md-2 "
                     style={darkTheme ? {borderColor: "white"} : null}
                >
                    {data.map((item) => (<div key={item.id} className={" MainPageSubreddits-options row"}
                                              style={darkTheme ? {borderColor: "white"} : null}
                    >
                        <img src={javaOneIcon} className={"col-md-2"} alt=""/>
                        <span className={"col-md-5"}
                              style={darkTheme ? {color: "white"} : {color: "black"}}
                              onClick={() => {
                                  alert(item.name)
                              }}
                        >{item.name}</span><br/><br/>
                    </div>))}
                </div>

            </div>
        </div>
    </div>);
};

export default MainPage;
