import React, { useContext } from "react";
import "./ViewPost.css";
import Comment from "../comment/Comment";
import data from "./ViewPostFakeApi.json";
import subreddit from "./SubredditFakeApi.json";
import upArrow from "../../../images/thumbUp.png";
import downArrow from "../../../images/thumbDown.png";
import javaIcon from "../../../images/javaIcon.jpg";
import springAndKafka from "../../../images/Spring&Kafka.png";
import parse from "html-react-parser";
import { Api } from "../../../context/ApiContext";
import { useEffect } from "react";
import { useLocation } from "react-router-dom";
function ViewPost() {
  const { darkTheme } = useContext(Api);
  useEffect(() => {
    document.title = "View Post";
  }, []);

  return (
    <div
      className="view-post row"
      style={darkTheme ? { backgroundColor: "black" } : null}
    >
      <div
        className="view-post-left col-md-8"
        style={darkTheme ? { borderColor: "white" } : null}
      >
        <div className={"row"}>
          {/* Like and DisLikes Section.*/}
          <div
            className="v-p-like col-1"
            style={
              darkTheme ? { backgroundColor: "black", color: "white" } : null
            }
          >
            {data.upVote ? (
              <img
                src={upArrow}
                alt=""
                className={"v-p-l-upVote-img"}
                style={{ background: "lightpink" }}
              />
            ) : (
              <img src={upArrow} alt="" className={"v-p-l-upVote-img"} />
            )}
            <br />
            <span className={"v-p-vote-count"}>{data.voteCount}</span>
            <br />
            {data.downVote ? (
              <img
                src={downArrow}
                alt=""
                className={"v-p-l-downVote-img"}
                style={{ background: "lightblue" }}
              />
            ) : (
              <img src={downArrow} alt="" className={"v-p-l-downVote-img"} />
            )}
          </div>
          <div
            className="post-content2 col-11"
            style={
              darkTheme ? { backgroundColor: "black", color: "white" } : null
            }
          >
            {/*Header Section.*/}
            <div className="header-post">
              <nav
                className={
                  darkTheme
                    ? "navbar navbar-expand-lg navbar-light bg-dark header-nav"
                    : "navbar navbar-expand-lg navbar-light bg-light header-nav"
                }
              >
                <img src={javaIcon} alt="" className={"post-subreddit-icon"} />
                <span
                  className="navbar-brand"
                  href="#"
                  style={darkTheme ? { color: "white" } : null}
                >
                  {data.subredditName}
                </span>
                <a className="nav-item nav-link active" href="#">
                  Posted By<span className="sr-only">_({data.userName})_</span>
                </a>
                <a className="nav-item nav-link active" href="#">
                  {data.duration}
                </a>
              </nav>
            </div>
            {/* Post Title Section */}
            <div className="title-post">
              <h5>{data.postName}</h5>
            </div>
            {/* Post Url*/}
            <div className="post-url">
              <a href={data.url} target={"_blank"}>
                Read More.
              </a>
            </div>
            {/* Post Description*/}
            <div className="description-post">{parse(data.description)}</div>
            {/* Post Image. */}
            {data.postImage !== null ? (
              <img className="image-post2" src={`data.postImage`} alt="" />
            ) : null}
          </div>
        </div>
      </div>
      <div className="view-post-mid col-md-1"></div>
      <div
        className="view-post-right col-md-3"
        style={darkTheme ? { borderColor: "white" } : null}
      >
        <img src={javaIcon} className={"subreddit-icon"} alt="" />
        <h5
          className={"v-p-r-subreddit-name"}
          style={
            darkTheme ? { backgroundColor: "black", color: "white" } : null
          }
        >
          {subreddit.name}
        </h5>
        <span
          style={
            darkTheme ? { backgroundColor: "black", color: "white" } : null
          }
        >
          {subreddit.description}
        </span>
        <button className={"subreddit-join-btn"}>Join Community</button>
      </div>
      <Comment />
    </div>
  );
}

export default ViewPost;
