import React, {useContext} from 'react';
import "./Post.css"
import data from "../../post/view-post/ViewPostFakeApi.json";
import upArrow from "../../../images/thumbUp.png";
import downArrow from "../../../images/thumbDown.png";
import parse from 'html-react-parser';
import javaIcon from "../../../images/javaIcon.jpg";
import springAndKafka from "../../../images/Spring&Kafka.png";
import {Api} from "../../../context/ApiContext";
import { useNavigate } from 'react-router-dom';

const Post = () => {
    const {darkTheme}=useContext(Api);
    let navigate = useNavigate();
    const handleRouting=(postId)=>{
        navigate("/viewPost")
    }
    return (
        <div className={"Post"} style={darkTheme?{background:"black"
            ,borderColor:"white"
        }:{background:"white"}}>
            <div className={"row test"}>
                {/* Like and DisLikes Section.*/}
                <div className="v-p-like col-1" style={darkTheme?{background:"black"}:{background:"white"}}>
                    {data.upVote ?
                        <img src={upArrow} alt="" className={"v-p-l-upVote-img"} style={{background: "lightpink"}}/>
                        : <img src={upArrow} alt="" className={"v-p-l-upVote-img"}/>
                    }
                    <br/>
                    <span className={"v-p-vote-count"}
                          style={darkTheme?{color:"white",}:null}
                    >{data.voteCount}</span>
                    <br/>
                    {data.downVote ?
                        <img src={downArrow} alt="" className={"v-p-l-downVote-img"}
                             style={darkTheme?{background:"white"}:null}/>
                        : <img src={downArrow} alt="" className={"v-p-l-downVote-img"}/>
                    }
                </div>
                <div className="post-content col-11"
                >
                    {/*Header Section.*/}
                    <div className="header-post">
                        <nav className="navbar navbar-expand-lg navbar-light bg-light header-nav">
                            <img src={javaIcon} alt="" className={"post-subreddit-icon"}/>
                            <a className="navbar-brand" href="#">{data.subredditName}</a>
                            <a className="nav-item nav-link active" href="#">Posted By<span
                                className="sr-only">_({data.userName})_</span></a>
                            <a className="nav-item nav-link active" href="#">{data.duration}</a>
                        </nav>
                    </div>
                    {/* Post Title Section */}
                    <div className="title-post">
                        <h5>{data.postName}</h5>
                    </div>
                    {/* Post Url*/}
                    <div className="post-url">
                        <a href={data.url} target={"_blank"}>Read More.</a>
                    </div>
                    {/* Post Description*/}
                    <div className="description-post">
                        {parse(data.description.slice(0,82))}
                        <span className={"ViewFullPost"}
                            onClick={()=>handleRouting(data.id)}
                        >ViewPost</span>
                    </div>
                    {/* Post Image. */}
                    <div className="image-post2">
                        <img className="image-post2" src={springAndKafka} alt=""/>
                    </div>

                </div>
            </div>
        </div>
    );
};

export default Post;
