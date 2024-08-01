import React, { useEffect, useState } from 'react';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

function PostList() {
  const [posts, setPosts] = useState([]);

  useEffect(() => {
    const fetchPosts = async () => {
      try {
        const response = await axios.get('/posts');
        setPosts(response.data);
      } catch (error) {
        console.error('Error fetching posts:', error);
      }
    };

    fetchPosts();
  }, []);

  return (
    <div className="container">
      <div className="list-group">
        {posts.map((post) => (
          <div key={post.id} className="list-group-item mb-3">
            <h5 className="mb-1">{post.title}</h5>
            <h6 className="mb-1 text-muted">
              {new Date(post.date).toLocaleDateString()}
            </h6>
            <p className="mb-1">{post.content}</p>
          </div>
        ))}
      </div>
    </div>
  );
}

export default PostList;
