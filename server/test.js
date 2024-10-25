const bcrypt = require('bcrypt');

bcrypt.hash("abc", null, function(err, hash) {
    console.log(hash)
})