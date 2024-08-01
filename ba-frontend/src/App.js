import React from 'react';
import PostList from './components/PostList';
import './App.css';

function App() {
  return (
    <div className="container-fluid">
      <header className="my-4 bg-dark text-white py-3">
        <h1 className="text-center">Blog z ciekawostkami pisany przez ChatGPT</h1>
      </header>
      <main>
        <h2 className="text-center">Wszystkie wpisy</h2>
        <div className="space-between"></div>
        <PostList />
      </main>
    </div>
  );
}

export default App;


