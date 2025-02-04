const passport = require('passport')
const LocalStrategy = require('passport-local').Strategy;
const passportJWT = require('passport-jwt')
const JWTStrategy = passportJWT.Strategy;
const extractJWT = passportJWT.ExtractJwt;
const bcrypt = require('bcrypt');
const { User, Sagam } = require('../models');

module.exports = () => {

    passport.use('user_local', new LocalStrategy({
        usernameField: 'email',
        passwordField: 'password'
    },async (email, password, done) => {
        try{
            const hashed = await new Promise((resolve) => {
                bcrypt.genSalt(10, function(err, salt) {
                      bcrypt.hash(password, salt, function(err, hash) {
                            resolve(hash)
                    })
                })
            })

            console.log(hashed)
            const user = await User.findOne({
                where: {
                    email: email,
                    password: hashed
                }
            });
            if (!user) done(null, false, {message:'이메일이나 비밀번호가 일치하지 않습니다.'});
            else {
                done(null, user);  
            }  
        }catch(error){
            done(error);
        }
        
    }));

    passport.use('sagam_local', new LocalStrategy({
        usernameField: 'email',
        passwordField: 'password'
    },async (email, password, done) => {
        try{
            const sagam = await Sagam.findOne({
                where: {
                    email: email,
                    password: password
                }
            });
            if (!sagam) done(null, false, {message:'이메일이나 비밀번호가 일치하지 않습니다.'});
            else {
                done(null, sagam);  
            }  
        }catch(error){
            done(error);
        }
        
    }));

    return passport;
}