import React, { useContext, useRef, useState } from "react";
import "./CreatePost.css";
import JoditEditor from "jodit-react";

import subreddit from "./SubredditsFakeApi.json";
import { Api } from "../../../context/ApiContext";
import { useEffect } from "react";

function CreatePost(props) {
  useEffect(() => {
    window.scroll(0, 0);
    document.title = "View Post";
  }, []);
  const editor = useRef(null);
  const [content, setContent] = useState("");
  const { darkTheme } = useContext(Api);

  const [postData, setPostData] = useState({
    subredditName: "",
    postName: "",
    url: "",
    description: "",
  });

  const handleSubmitPost = () => {
    if (postData.postName.length<=0){
      alert("Post Name cannot be empty!!");
    }else if(postData.subredditName<=0){
      alert("Select Subreddit!!");
    }else{
      console.log(postData);
    }
  };

  return (
    <form
      className={"create-post"}
      style={darkTheme ? { backgroundColor: "black", color: "white" } : null}
    >
      <div className="form-group">
        <label htmlFor="formGroupExampleInput">Post Name</label>
        <input
          type="text"
          className="form-control "
          placeholder="Enter Title of your post"
          onChange={(event) => {
            setPostData({ ...postData, postName: event.target.value });
          }}
        />
      </div>
      <div className="form-group postDesc">
        <JoditEditor
          ref={editor}
          value={content}
          config={{
            theme: darkTheme ? "dark" : null,
          }}
          tabIndex={1} // tabIndex of textarea
          onChange={(newContent) => {
            setPostData({
              ...postData,
              description: newContent,
            });
          }}
        />
      </div>
      <div className="row">
        <div className="postUrl col-7">
          <span className={"m-2"}>Url</span>
          <input
            type="text"
            placeholder={"@Enter URL for post "}
            onChange={(event) => {
              setPostData({ ...postData, url: event.target.value });
            }}
          />
        </div>

        <div className="col m-2">
          <select className="form-control" style={{ marginTop: 10 }}>
            <option selected disabled defaultValue={"Select"}>
              Select Subreddit
            </option>
            <option>None</option>
            {subreddit.map((data) => (
              <option
                key={data.id}
                onClick={(event) => {
                  setPostData({
                    ...postData,
                    subredditName: event.target.value,
                  });
                }}
              >
                {data.name}
              </option>
            ))}
          </select>
        </div>
      </div>
      <button
        type={"button"}
        className={"btn btn-outline-primary submitBtn"}
        onClick={handleSubmitPost}
      >
        Create Post
      </button>
    </form>
  );
}

export default CreatePost;
