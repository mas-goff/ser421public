# MicroBlog example

This example was inspired by an article by C. Reyes on creating a server-side version of a Microblog using NodeJS and Express.
https://www.sitepoint.com/build-microblog-node-js-git-markdown

- This version uses AJAX fetch API to get list of available/uploaded blogs.
- To add a new blog, it again uses POST with AJAX fetch.
- Please check view/index.html. It has inline javascript, which has Fetch API calls.
- It also uses DOM and javascript events. For e.g. onLoad event on body element to get list of blogs.
- Everything else is still the same as Express MicroBlog example.

# Originally based on the pure Node.js MicroBlog example

This version of MicroBlog uses Express framework.
1. Run `npm install`
2. Run `node app.js`
3. Visit http://localhost:1337/
4. To access a blog - http://localhost:1337/blog/<blog_name_here>


## License (for original code on Sitepoint)

The MIT License (MIT)

Copyright (c) 2017 SitePoint

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
