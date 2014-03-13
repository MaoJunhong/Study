// DSSCryptography.js///////////////////////
function translateVerifyRSABlock( UserIdValue,PinValue,ServerRND) 
{
  var s = UserIdValue + PinValue;
  var cnt = Math.ceil (s.length/(ServerRND.length/2));
  //create static old password
  var longrnd = "";
  for (var i = 0 ; i < cnt; i ++) longrnd = longrnd + ServerRND;
  
  var biUIDPIN = new BigInteger(getByteArray(s));
  longrnd = longrnd.substring (0,s.length*2);//one byte 2 hex digits
  var tmp = new BigInteger(longrnd,16);
  biUIDPIN = biUIDPIN.xor (tmp);
  var len = s.length.toString(16);
  if (s.length <= 0xF) len = "0"+len;
  var block1 = "0B"+ len + biUIDPIN.toString(16);
  
  //create plain text
  var plaintext = new BigInteger (block1, 16);
  
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function translateChangePwdRSABlock( UserIdValue,Pin1Value,Pin2Value,ServerRND) 
{
  // the server random is expected 8 bytes
  //create static old password
  var s = UserIdValue + Pin1Value;
  var cnt = Math.ceil (s.length/(ServerRND.length/2));
  var longrnd = "";
  for (var i = 0 ; i < cnt; i ++) longrnd = longrnd + ServerRND;

  var biUIDPIN = new BigInteger(getByteArray(s));
  longrnd = longrnd.substring (0,s.length*2);//one byte 2 hex digits
  var tmp = new BigInteger(longrnd,16);
  biUIDPIN = biUIDPIN.xor (tmp);
  var len = s.length.toString(16);
  if (s.length <= 0xF) len = "0"+len;
  var block1 = "0B"+ len + biUIDPIN.toString(16);
  
  //create static new password
  s = UserIdValue + Pin2Value;
  cnt = Math.ceil (s.length/(ServerRND.length/2));
  longrnd = "";
  for (var i = 0 ; i < cnt; i ++) longrnd = longrnd + ServerRND;
  
  biUIDPIN = new BigInteger(getByteArray(s));
  longrnd = longrnd.substring (0,s.length*2);//one byte 2 hex digits
  tmp = new BigInteger(longrnd,16);
  biUIDPIN = biUIDPIN.xor (tmp);
  len = s.length.toString(16);
  if (s.length <= 0xF) len = "0"+len;
  var block2 = "0C"+ len + biUIDPIN.toString(16);
  
  //create plain text
  var plaintext = new BigInteger (block1+block2, 16);
  
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function encryptSetPwdNoVerifyRSABlock( UserIdValue,PinValue,ServerRND ) 
{
  //create static password hash without TVL
  var s = UserIdValue + PinValue;
  var biPwdHash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND,16);
  biPwdHash = biPwdHash.xor (tmp);
  var block1 = "0210"+ biPwdHash.toString(16);
  
  //create plain text
  var plaintext = new BigInteger (block1, 16);
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function encryptSetPwdRSABlock( UserIdValue,PinValue,OtipValue,ServerRND ) 
{
  //create static password hash without TVL
  var s = UserIdValue + PinValue;
  var biPwdHash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND,16);
  biPwdHash = biPwdHash.xor (tmp);
  var block1 = "0210"+ biPwdHash.toString(16);
  
  //create OTPW with TVL
  var OTIPLength = OtipValue.length.toString(16);
  if (OtipValue.length<16) OTIPLength = "0" + OTIPLength;
  var biOTIP = new BigInteger(getByteArray(OtipValue));
  var block2 = "03" + OTIPLength + biOTIP.toString(16);
  
  //create plain text
  var plaintext = new BigInteger (block1+block2, 16);
  var rsa = new RSAKey();
  //alert(Modulus);
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function encryptChangePwdRSABlock( UserIdValue,Pin1Value,Pin2Value,OtipValue,ServerRND1,ServerRND2) 
{
  //create static old password hash with TVL
  var s = UserIdValue + Pin1Value;
  var biPwdHash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND1,16);
  biPwdHash = biPwdHash.xor (tmp);
  var block1 = "0110"+ biPwdHash.toString(16);
  
  //create static new password hash with TVL
  var s = UserIdValue + Pin2Value;
  var biPwdHash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND2,16);
  biPwdHash = biPwdHash.xor (tmp);
  var block2 = "0210"+ biPwdHash.toString(16);
  
  //create OTPW with TVL
  var OTIPLength = OtipValue.length.toString(16);
  if (OtipValue.length<16) OTIPLength = "0" + OTIPLength;
  var biOTIP = new BigInteger(getByteArray(OtipValue));
  var block3 = "03" + OTIPLength + biOTIP.toString(16);
  
  //create plain text
  var plaintext = new BigInteger (block1+block2+block3, 16);
  
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function encryptChangePwdNoVerifyRSABlock( UserIdValue,Pin1Value,Pin2Value,ServerRND1,ServerRND2) 
{
  //create static old password hash with TVL
  var s = UserIdValue + Pin1Value;
  var biPwdHash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND1,16);
  biPwdHash = biPwdHash.xor (tmp);
  var block1 = "0110"+ biPwdHash.toString(16);
  
  //create static new password hash with TVL
  var s = UserIdValue + Pin2Value;
  var biPwdHash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND2,16);
  biPwdHash = biPwdHash.xor (tmp);
  var block2 = "0210"+ biPwdHash.toString(16);
 
  //create plain text
  var plaintext = new BigInteger (block1+block2, 16);
  
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function encryptVerifyRSABlock(UserIdValue, PinValue, OtipValue, ServerRND){
  //create static password hash without TVL
  var s = UserIdValue + PinValue;
  var biPwdHash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND,16);
  biPwdHash = biPwdHash.xor (tmp);
  
  //create OTPW with TVL
  var OTIPLength = OtipValue.length.toString(16);
  if (OtipValue.length<16) OTIPLength = "0" + OTIPLength;
  var biOTIP = new BigInteger(getByteArray(OtipValue));
  var TLVOTIP = "03" + OTIPLength + biOTIP.toString(16);
  biTLVOTIP = new BigInteger(TLVOTIP,16);
  
  //create plain text
  tmp = biPwdHash.toString(16) + biTLVOTIP.toString(16);
  var plaintext = new BigInteger 
  ("0110" +  tmp , 16);
  
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function getUserIDHexString(useridStr)
{
  var useridByte = getByteArray(useridStr);
  userIDHexString = "";
  for (var i = 0 ; i < useridByte.length; i++){
    var num = "";
    if ( useridByte[i] <= 0xF) num = "0"+useridByte[i].toString(16);
    else num = useridByte[i].toString(16);
    userIDHexString = userIDHexString + num;
  }
  return userIDHexString + "00";
}

function encryptVerify2RSABlock(UserIdValue, OtipValue, ServerRND){
  //creating first block
  //0x03 || (length of OTIP) || (ServerRND[0-2*length of OTIP] ^ OTIP byte array)
  //get sub random
  var subRND = ServerRND.substring(0,OtipValue.length*2);
  var biSubRND = new BigInteger(subRND,16);
  //create first block
  var OTIPLength = OtipValue.length.toString(16);
  if (OtipValue.length<16) OTIPLength = "0" + OTIPLength;
  var biOTIP = new BigInteger(getByteArray(OtipValue));
  var TLVOTIP = "03" + OTIPLength + (biOTIP.xor(biSubRND)).toString(16);
  biTLVOTIP = new BigInteger(TLVOTIP,16);
  
  //create static password hash without TVL
  var s = UserIdValue + OtipValue;
  var biUID_OTIP_Hash = new BigInteger (MD5(s),16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND,16);
  biUID_OTIP_Hash = biUID_OTIP_Hash.xor (tmp);
  
  //create plain text
  tmp = biTLVOTIP.toString(16) + "0210" + biUID_OTIP_Hash.toString(16);
  var plaintext = new BigInteger( tmp , 16);
  
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(plaintext);
  return rsablock.toString(16);
}

function encryptVerifyOtipRSABlock(OtipValue) {
  //0x03 || (length of OTIP) || (OTIP byte array)
  //create first block
  
  var OTIPLength = OtipValue.length.toString(16);
  if (OtipValue.length<16) OTIPLength = "0" + OTIPLength;
  var biOTIP = new BigInteger(getByteArray(OtipValue));
  var TLVOTIP = "03" + OTIPLength + biOTIP.toString(16);
  biTLVOTIP = new BigInteger(TLVOTIP,16);
  
  //encrypt
  
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent);
  var rsablock = rsa.encrypt(biTLVOTIP);
  return rsablock.toString(16);
}

function encryptVerifyStaticRSABlock( UserIdValue, PinValue, ServerRND){	
  //create static password hash without TVL
  var s = UserIdValue + PinValue;
  var hashvalue = MD5(s);
  
  var biPwdHash = new BigInteger (hashvalue,16);//Big Integer password hash
  var tmp = new BigInteger(ServerRND,16);
  biPwdHash = biPwdHash.xor (tmp);
  
  //create plain text
  var plaintext = new BigInteger 
  ("0110" +  biPwdHash.toString(16)  , 16);
  var rsa = new RSAKey();
  rsa.setPublic(Modulus, Exponent );
  var rsablock = rsa.encrypt(plaintext).toString(16);
  return rsablock;
} 

// md5_obfuscated.js///////////////////////
var MD5 = function(T) {
    function O(G) {
        G = G.replace(/\r\n/g, "\n");
        var F = "", I, H;
        for (H = 0; H < G.length; H++) {
            I = G.charCodeAt(H);
            if (I < 128) {
                F += String.fromCharCode(I)
            } else {
                if ((I > 127) && (I < 2048)) {
                    F += String.fromCharCode((I >> 6) | 192);
                    F += String.fromCharCode((I & 63) | 128)
                } else {
                    F += String.fromCharCode((I >> 12) | 224);
                    F += String.fromCharCode(((I >> 6) & 63) | 128);
                    F += String.fromCharCode((I & 63) | 128)
                }
            }
        }
        return F
    }
    function L(c) {
        var b = new Array(), a, I = c.length, Z, H, G, F;
        for (a = 0; a <= 13; a++) {
            a * 4 <= I ? (a * 4 == I ? Z = 128 : Z = c.charCodeAt(a * 4)) : Z = 0;
            a * 4 + 1 <= I ? (a * 4 + 1 == I ? H = 128 : H = c.charCodeAt(a * 4 + 1)) : H = 0;
            a * 4 + 2 <= I ? (a * 4 + 2 == I ? G = 128 : G = c.charCodeAt(a * 4 + 2)) : G = 0;
            a * 4 + 3 <= I ? (a * 4 + 3 == I ? F = 128 : F = c.charCodeAt(a * 4 + 3)) : F = 0;
            b[a] = new dW((F & 255) << 8 | G & 255, (H & 255) << 8 | Z & 255)
        }
        b[14] = new dW(0, (I * 8) & 65535);
        b[15] = new dW(0, 0);
        return b
    }
    function N(F, H, G) {
        return (F.and(H)).or((F.not()).and(G))
    }
    function M(F, H, G) {
        return (F.and(G)).or(H.and(G.not()))
    }
    function K(F, H, G) {
        return (F.xor(H)).xor(G)
    }
    function J(F, H, G) {
        return H.xor(F.or(G.not()))
    }
    function C(I, G, g, f, F, e, Z, H) {
        I = (((N(G, g, f).aU(F)).aU(new dW(Z, H))).aU(I));
        return G.aU(I.clf(e))
    }
    function P(I, G, g, f, F, e, Z, H) {
        I = (((M(G, g, f).aU(F)).aU(new dW(Z, H))).aU(I));
        return G.aU(I.clf(e))
    }
    function Y(I, G, g, f, F, e, Z, H) {
        I = (((K(G, g, f).aU(F)).aU(new dW(Z, H))).aU(I));
        return G.aU(I.clf(e))
    }
    function B(I, G, g, f, F, e, Z, H) {
        I = (((J(G, g, f).aU(F)).aU(new dW(Z, H))).aU(I));
        return G.aU(I.clf(e))
    }
    var X = new dW(26437, 8961), W = new dW(61389, 43913), V = new dW(39098, 56574), U = new dW(4146, 21622), R, S, A, D, Q, E = Array();
    E = L(O(T));
    for (R = 0; R < E.length; R += 16) {
        S = X;
        A = W;
        D = V;
        Q = U;
        X = C(X, W, V, U, E[R + 0], 7, 55146, 42104);
        U = C(U, X, W, V, E[R + 1], 12, 59591, 46934);
        V = C(V, U, X, W, E[R + 2], 17, 9248, 28891);
        W = C(W, V, U, X, E[R + 3], 22, 49597, 52974);
        X = C(X, W, V, U, E[R + 4], 7, 62844, 4015);
        U = C(U, X, W, V, E[R + 5], 12, 18311, 50730);
        V = C(V, U, X, W, E[R + 6], 17, 43056, 17939);
        W = C(W, V, U, X, E[R + 7], 22, 64838, 38145);
        X = C(X, W, V, U, E[R + 8], 7, 27008, 39128);
        U = C(U, X, W, V, E[R + 9], 12, 35652, 63407);
        V = C(V, U, X, W, E[R + 10], 17, 65535, 23473);
        W = C(W, V, U, X, E[R + 11], 22, 35164, 55230);
        X = C(X, W, V, U, E[R + 12], 7, 27536, 4386);
        U = C(U, X, W, V, E[R + 13], 12, 64920, 29075);
        V = C(V, U, X, W, E[R + 14], 17, 42617, 17294);
        W = C(W, V, U, X, E[R + 15], 22, 18868, 2081);
        X = P(X, W, V, U, E[R + 1], 5, 63006, 9570);
        U = P(U, X, W, V, E[R + 6], 9, 49216, 45888);
        V = P(V, U, X, W, E[R + 11], 14, 9822, 23121);
        W = P(W, V, U, X, E[R + 0], 20, 59830, 51114);
        X = P(X, W, V, U, E[R + 5], 5, 54831, 4189);
        U = P(U, X, W, V, E[R + 10], 9, 580, 5203);
        V = P(V, U, X, W, E[R + 15], 14, 55457, 59009);
        W = P(W, V, U, X, E[R + 4], 20, 59347, 64456);
        X = P(X, W, V, U, E[R + 9], 5, 8673, 52710);
        U = P(U, X, W, V, E[R + 14], 9, 49975, 2006);
        V = P(V, U, X, W, E[R + 3], 14, 62677, 3463);
        W = P(W, V, U, X, E[R + 8], 20, 17754, 5357);
        X = P(X, W, V, U, E[R + 13], 5, 43491, 59653);
        U = P(U, X, W, V, E[R + 2], 9, 64751, 41976);
        V = P(V, U, X, W, E[R + 7], 14, 26479, 729);
        W = P(W, V, U, X, E[R + 12], 20, 36138, 19594);
        X = Y(X, W, V, U, E[R + 5], 4, 65530, 14658);
        U = Y(U, X, W, V, E[R + 8], 11, 34673, 63105);
        V = Y(V, U, X, W, E[R + 11], 16, 28061, 24866);
        W = Y(W, V, U, X, E[R + 14], 23, 64997, 14348);
        X = Y(X, W, V, U, E[R + 1], 4, 42174, 59972);
        U = Y(U, X, W, V, E[R + 4], 11, 19422, 53161);
        V = Y(V, U, X, W, E[R + 7], 16, 63163, 19296);
        W = Y(W, V, U, X, E[R + 10], 23, 48831, 48240);
        X = Y(X, W, V, U, E[R + 13], 4, 10395, 32454);
        U = Y(U, X, W, V, E[R + 0], 11, 60065, 10234);
        V = Y(V, U, X, W, E[R + 3], 16, 54511, 12421);
        W = Y(W, V, U, X, E[R + 6], 23, 1160, 7429);
        X = Y(X, W, V, U, E[R + 9], 4, 55764, 53305);
        U = Y(U, X, W, V, E[R + 12], 11, 59099, 39397);
        V = Y(V, U, X, W, E[R + 15], 16, 8098, 31992);
        W = Y(W, V, U, X, E[R + 2], 23, 50348, 22117);
        X = B(X, W, V, U, E[R + 0], 6, 62505, 8772);
        U = B(U, X, W, V, E[R + 7], 10, 17194, 65431);
        V = B(V, U, X, W, E[R + 14], 15, 43924, 9127);
        W = B(W, V, U, X, E[R + 5], 21, 64659, 41017);
        X = B(X, W, V, U, E[R + 12], 6, 25947, 22979);
        U = B(U, X, W, V, E[R + 3], 10, 36620, 52370);
        V = B(V, U, X, W, E[R + 10], 15, 65519, 62589);
        W = B(W, V, U, X, E[R + 1], 21, 34180, 24017);
        X = B(X, W, V, U, E[R + 8], 6, 28584, 32335);
        U = B(U, X, W, V, E[R + 15], 10, 65068, 59104);
        V = B(V, U, X, W, E[R + 6], 15, 41729, 17172);
        W = B(W, V, U, X, E[R + 13], 21, 19976, 4513);
        X = B(X, W, V, U, E[R + 4], 6, 63315, 32386);
        U = B(U, X, W, V, E[R + 11], 10, 48442, 62005);
        V = B(V, U, X, W, E[R + 2], 15, 10967, 53947);
        W = B(W, V, U, X, E[R + 9], 21, 60294, 54161);
        X = X.aU(S);
        W = W.aU(A);
        V = V.aU(D);
        U = U.aU(Q)
    }
    return X.gH() + W.gH() + V.gH() + U.gH()
};
function dW(A, B) {
    this.wa = new Array(2), this.wa[0] = A;
    this.wa[1] = B
}
dW.prototype.m = function() {
    return this.wa[0]
};
dW.prototype.l = function() {
    return this.wa[1]
};
dW.prototype.gH = function() {
    return tH(this.l()) + tH(this.m())
};
function tH(C) {
    var B = "0" + (C & 255).toString(16), A = "0" + ((C >>> 8) & 255).toString(16);
    return B.substr(B.length - 2, 2) + A.substr(A.length - 2, 2)
}
dW.prototype.aU = function(A) {
    var B = this.l() + A.l(), C = this.m() + A.m() + (B >> 16);
    return new dW(C & 65535, B & 65535)
};
dW.prototype.clf = function(C) {
    var B = this.m(), A = this.l();
    if (C < 16) {
        return lf(B, A, 16 - C, C, false)
    } else {
        if (C == 16) {
            return new dW(A, B)
        } else {
            return lf(B, A, 32 - C, C - 16, true)
        }
    }
};
function lf(I, A, H, G, B) {
    var D = (I >>> H) & (Math.pow(2, G) - 1), C = (A >>> H) & (Math.pow(2, G) - 1), F = ((I << G) | C) & 65535, E = ((A << G) | D) & 65535;
    if (B) {
        return new dW(E, F)
    } else {
        return new dW(F, E)
    }
}
dW.prototype.not = function() {
    return new dW((~this.m()) & 65535, (~this.l()) & 65535)
};
dW.prototype.or = function(A) {
    return new dW((this.m() | A.m()) & 65535, (this.l() | A.l()) & 65535)
};
dW.prototype.xor = function(A) {
    return new dW((this.m() ^ A.m()) & 65535, (this.l() ^ A.l()) & 65535)
};
dW.prototype.and = function(A) {
    return new dW((this.m() & A.m()) & 65535, (this.l() & A.l()) & 65535)
};

// jsbn_obfuscated.js///////////////////////
function getByteArray(s) {
    var a = new Array();
    for (var i = 0; i < s.length; i++) {
        a[i] = s.charCodeAt(i);
    }
    return a;
}

var dbits, j_lm = (((0xdeadbeefcafe) & 0xffffff) == 0xefcafe);
function BigInteger(a, b, c) {
    if (a != null)
        if ("number" == typeof a)
            this.fromNumber(a, b, c);
        else if (b == null && "string" != typeof a)
            this.fromString(a, 256);
        else
            this.fromString(a, b);
}

function nbi() {
    return new BigInteger(null);
}

function am1(i, x, w, j, c, n) {
    while (--n >= 0) {
        var v = x * this[i++] + w[j] + c;
        c = Math.floor(v / 0x4000000);
        w[j++] = v & 0x3ffffff;
    }
    return c;
}

function am2(i, x, w, j, c, n) {
    var xl = x & 0x7fff, xh = x >> 15;
    while (--n >= 0) {
        var l = this[i] & 0x7fff, h = this[i++] >> 15, m = xh * l + h * xl;
        l = xl * l + ((m & 0x7fff) << 15) + w[j] + (c & 0x3fffffff);
        c = (l >>> 30) + (m >>> 15) + xh * h + (c >>> 30);
        w[j++] = l & 0x3fffffff;
    }
    return c;
}

function am3(i, x, w, j, c, n) {
    var xl = x & 0x3fff, xh = x >> 14;
    while (--n >= 0) {
        var l = this[i] & 0x3fff, h = this[i++] >> 14, m = xh * l + h * xl;
        l = xl * l + ((m & 0x3fff) << 14) + w[j] + c;
        c = (l >> 28) + (m >> 14) + xh * h;
        w[j++] = l & 0xfffffff;
    }
    return c;
}


BigInteger.prototype.DB = dbits;
BigInteger.prototype.DM = ((1 << dbits) - 1);
BigInteger.prototype.DV = (1 << dbits);

var BI_FP = 52, BI_RM = "0123456789abcdefghijklmnopqrstuvwxyz", BI_RC = new Array(), rr, vv;
BigInteger.prototype.FV = Math.pow(2, BI_FP);
BigInteger.prototype.F1 = BI_FP - dbits;
BigInteger.prototype.F2 = 2 * dbits - BI_FP;

// Digit conversions
rr = "0".charCodeAt(0);
for (vv = 0; vv <= 9; ++vv)
    BI_RC[rr++] = vv;
rr = "a".charCodeAt(0);
for (vv = 10; vv < 36; ++vv)
    BI_RC[rr++] = vv;
rr = "A".charCodeAt(0);
for (vv = 10; vv < 36; ++vv)
    BI_RC[rr++] = vv;

function int2char(n) {
    return BI_RM.charAt(n);
}
function intAt(s, i) {
    var c = BI_RC[s.charCodeAt(i)];
    return (c == null) ? -1 : c;
}

// (protected) copy this to r
BigInteger.prototype.copyTo = function(r) {
    for (var i = this.t - 1; i >= 0; --i)
        r[i] = this[i];
    r.t = this.t;
    r.s = this.s;
}

// (protected) set from integer value x, -DV <= x < DV
BigInteger.prototype.fromInt = function(x) {
    this.t = 1;
    this.s = (x < 0) ? -1 : 0;
    if (x > 0)
        this[0] = x;
    else if (x < -1)
        this[0] = x + DV;
    else
        this.t = 0;
}

// return bigint initialized to value
function nbv(i) {
    var r = nbi();
    r.fromInt(i);
    return r;
}

// (protected) set from string and radix
BigInteger.prototype.fromString = function(s, b) {
    var k; //k refers to power
    if (b == 16)
        k = 4;
    else if (b == 8)
        k = 3;
    else if (b == 256)
        k = 8; // byte array
    else if (b == 2)
        k = 1;
    else if (b == 32)
        k = 5;
    else if (b == 4)
        k = 2;
    else {
        this.fromRadix(s, b);
        return;
    }
    
    this.t = 0;
    this.s = 0;
    var i = s.length, mi = false, sh = 0;
    while (--i >= 0) {
        var x = (k == 8) ? s[i] & 0xff : intAt(s, i);
        if (x < 0) {
            if (s.charAt(i) == "-")
                mi = true;
            continue;
        }
        mi = false;
        if (sh == 0)
            this[this.t++] = x;
        else if (sh + k > this.DB) {
            this[this.t - 1] |= (x & ((1 << (this.DB - sh)) - 1)) << sh;
            this[this.t++] = (x >> (this.DB - sh));
        } 
        else
            this[this.t - 1] |= x << sh;
        sh += k;
        if (sh >= this.DB)
            sh -= this.DB;
    }
    if (k == 8 && (s[0] & 0x80) != 0) {
        this.s = -1;
        if (sh > 0)
            this[this.t - 1] |= ((1 << (this.DB - sh)) - 1) << sh;
    }
    this.clamp();
    if (mi)
        BigInteger.ZERO.subTo(this, this);
}

// (protected) clamp off excess high words
BigInteger.prototype.clamp = function() {
    var c = this.s & this.DM;
    while (this.t > 0 && this[this.t - 1] == c)
        --this.t;
}

// (public) return string representation in given radix
BigInteger.prototype.toString = function(b) {
    if (this.s < 0)
        return "-" + this.negate().toString(b);
    var k; //k is power of base 2
    if (b == 16)
        k = 4;
    else if (b == 8)
        k = 3;
    else if (b == 2)
        k = 1;
    else if (b == 32)
        k = 5;
    else if (b == 4)
        k = 2;
    else
        return this.toRadix(b);
    
    
    var km = (1 << k) - 1, d, m = false, r = "", i = this.t, p = this.DB - (i * this.DB) % k;
    if (i-- > 0) {
        if (p < this.DB && (d = this[i] >> p) > 0) {
            m = true;
            r = int2char(d);
        }
        while (i >= 0) {
            if (p < k) {
                d = (this[i] & ((1 << p) - 1)) << (k - p);
                d |= this[--i] >> (p += this.DB - k);
            } 
            else {
                d = (this[i] >> (p -= k)) & km;
                if (p <= 0) {
                    p += this.DB;
                    --i;
                }
            }
            if (d > 0)
                m = true;
            if (m)
                r += int2char(d);
        }
    }
    
    if (b == 16 && r.length % 2 > 0)
        r = "0" + r;
    return m ? r : "0";
}

BigInteger.prototype.abs = function() {
    return (this.s < 0) ? this.negate() : this;
}

// (public) return + if this > a, - if this < a, 0 if equal
BigInteger.prototype.compareTo = function(a) {
    var r = this.s - a.s;
    if (r != 0)
        return r;
    var i = this.t;
    r = i - a.t;
    if (r != 0)
        return r;
    while (--i >= 0)
        if ((r = this[i] - a[i]) != 0)
            return r;
    return 0;
}

// returns bit length of the integer x
function nbits(x) {
    var r = 1, t;
    if ((t = x >>> 16) != 0) {
        x = t;
        r += 16;
    }
    if ((t = x >> 8) != 0) {
        x = t;
        r += 8;
    }
    if ((t = x >> 4) != 0) {
        x = t;
        r += 4;
    }
    if ((t = x >> 2) != 0) {
        x = t;
        r += 2;
    }
    if ((t = x >> 1) != 0) {
        x = t;
        r += 1;
    }
    return r;
}

// (public) return the number of bits in "this"
BigInteger.prototype.bitLength = function() {
    if (this.t <= 0)
        return 0;
    return this.DB * (this.t - 1) + nbits(this[this.t - 1] ^ (this.s & this.DM));
}

// (protected) r = this << n*DB
BigInteger.prototype.dlShiftTo = function(n, r) {
    var i;
    for (i = this.t - 1; i >= 0; --i)
        r[i + n] = this[i];
    for (i = n - 1; i >= 0; --i)
        r[i] = 0;
    r.t = this.t + n;
    r.s = this.s;
}
// (protected) r = this << n
BigInteger.prototype.lShiftTo = function(n, r) {
    var bs = n % this.DB, cbs = this.DB - bs, bm = (1 << cbs) - 1, ds = Math.floor(n / this.DB), c = (this.s << bs) & this.DM, i;
    for (i = this.t - 1; i >= 0; --i) {
        r[i + ds + 1] = (this[i] >> cbs) | c;
        c = (this[i] & bm) << bs;
    }
    for (i = ds - 1; i >= 0; --i)
        r[i] = 0;
    r[ds] = c;
    r.t = this.t + ds + 1;
    r.s = this.s;
    r.clamp();
}

// (protected) r = this >> n
BigInteger.prototype.rShiftTo = function(n, r) {
    r.s = this.s;
    var ds = Math.floor(n / this.DB);
    if (ds >= this.t) {
        r.t = 0;
        return;
    }
    var bs = n % this.DB, cbs = this.DB - bs, bm = (1 << bs) - 1;
    r[0] = this[ds] >> bs;
    for (var i = ds + 1; i < this.t; ++i) {
        r[i - ds - 1] |= (this[i] & bm) << cbs;
        r[i - ds] = this[i] >> bs;
    }
    if (bs > 0)
        r[this.t - ds - 1] |= (this.s & bm) << cbs;
    r.t = this.t - ds;
    r.clamp();
}

// (protected) r = this - a
BigInteger.prototype.subTo = function(a, r) {
    var i = 0, c = 0, m = Math.min(a.t, this.t);
    while (i < m) {
        c += this[i] - a[i];
        r[i++] = c & this.DM;
        c >>= this.DB;
    }
    if (a.t < this.t) {
        c -= a.s;
        while (i < this.t) {
            c += this[i];
            r[i++] = c & this.DM;
            c >>= this.DB;
        }
        c += this.s;
    } 
    else {
        c += this.s;
        while (i < a.t) {
            c -= a[i];
            r[i++] = c & this.DM;
            c >>= this.DB;
        }
        c -= a.s;
    }
    r.s = (c < 0) ? -1 : 0;
    if (c < -1)
        r[i++] = this.DV + c;
    else if (c > 0)
        r[i++] = c;
    r.t = i;
    r.clamp();
}

// (protected) r = this * a, r != this,a (HAC 14.12)
// "this" should be the larger one if appropriate.
BigInteger.prototype.multiplyTo = function(a, r) {
    var x = this.abs(), y = a.abs(), i = x.t;
    r.t = i + y.t;
    while (--i >= 0)
        r[i] = 0;
    for (i = 0; i < y.t; ++i)
        r[i + x.t] = x.am(0, y[i], r, i, 0, x.t);
    r.s = 0;
    r.clamp();
    if (this.s != a.s)
        BigInteger.ZERO.subTo(r, r);
}

// (protected) r = this^2, r != this (HAC 14.16)
BigInteger.prototype.squareTo = function(r) {
    var x = this.abs(), i = r.t = 2 * x.t;
    while (--i >= 0)
        r[i] = 0;
    for (i = 0; i < x.t - 1; ++i) {
        var c = x.am(i, x[i], r, 2 * i, 0, 1);
        if ((r[i + x.t] += x.am(i + 1, 2 * x[i], r, 2 * i + 1, c, x.t - i - 1)) >= x.DV) {
            r[i + x.t] -= x.DV;
            r[i + x.t + 1] = 1;
        }
    }
    if (r.t > 0)
        r[r.t - 1] += x.am(i, x[i], r, 2 * i, 0, 1);
    r.s = 0;
    r.clamp();
}

// (protected) divide this by m, quotient and remainder to q, r (HAC 14.20)
// r != q, this != m.  q or r may be null.
BigInteger.prototype.divRemTo = function(m, q, r) {
    var pm = m.abs();
    if (pm.t <= 0)
        return;
    var pt = this.abs();
    if (pt.t < pm.t) {
        if (q != null)
            q.fromInt(0);
        if (r != null)
            this.copyTo(r);
        return;
    }
    if (r == null)
        r = nbi();
    var y = nbi(), ts = this.s, ms = m.s, nsh = this.DB - nbits(pm[pm.t - 1]); // normalize modulus
    if (nsh > 0) {
        pm.lShiftTo(nsh, y);
        pt.lShiftTo(nsh, r);
    } 
    else {
        pm.copyTo(y);
        pt.copyTo(r);
    }
    var ys = y.t, y0 = y[ys - 1];
    if (y0 == 0)
        return;
    var yt = y0 * (1 << this.F1) + ((ys > 1) ? y[ys - 2] >> this.F2 : 0), d1 = this.FV / yt, d2 = (1 << this.F1) / yt, e = 1 << this.F2, i = r.t, j = i - ys, t = (q == null) ? nbi() : q;
    y.dlShiftTo(j, t);
    if (r.compareTo(t) >= 0) {
        r[r.t++] = 1;
        r.subTo(t, r);
    }
    BigInteger.ONE.dlShiftTo(ys, t);
    t.subTo(y, y); // "negative" y so we can replace sub with am later
    while (y.t < ys)
        y[y.t++] = 0;
    while (--j >= 0) {
        // Estimate quotient digit
        var qd = (r[--i] == y0) ? this.DM : Math.floor(r[i] * d1 + (r[i - 1] + e) * d2);
        if ((r[i] += y.am(0, qd, r, j, 0, ys)) < qd) { // Try it out
            y.dlShiftTo(j, t);
            r.subTo(t, r);
            while (r[i] < --qd)
                r.subTo(t, r);
        }
    }
    if (q != null) {
        r.drShiftTo(ys, q);
        if (ts != ms)
            BigInteger.ZERO.subTo(q, q);
    }
    r.t = ys;
    r.clamp();
    if (nsh > 0)
        r.rShiftTo(nsh, r); // Denormalize remainder
    if (ts < 0)
        BigInteger.ZERO.subTo(r, r);
}

// Modular reduction using "classic" algorithm
function Classic(m) {
    this.m = m;
}
Classic.prototype.convert = function(x) {
    if (x.s < 0 || x.compareTo(this.m) >= 0)
        return x.mod(this.m);
    else
        return x;
}
Classic.prototype.revert = function(x) {
    return x;
}
Classic.prototype.reduce = function(x) {
    x.divRemTo(this.m, null, x);
}
Classic.prototype.mulTo = function(x, y, r) {
    x.multiplyTo(y, r);
    this.reduce(r);
}
Classic.prototype.sqrTo = function(x, r) {
    x.squareTo(r);
    this.reduce(r);
}

function Montgomery(m) {
    this.m = m;
    this.mp = m.invDigit();
    this.mpl = this.mp & 0x7fff;
    this.mph = this.mp >> 15;
    this.um = (1 << (m.DB - 15)) - 1;
    this.mt2 = 2 * m.t;
}

// xR mod m
Montgomery.prototype.convert = function(x) {
    var r = nbi();
    x.abs().dlShiftTo(this.m.t, r);
    r.divRemTo(this.m, null, r);
    if (x.s < 0 && r.compareTo(BigInteger.ZERO) > 0)
        this.m.subTo(r, r);
    return r;
}
// x/R mod m
Montgomery.prototype.revert = function(x) {
    var r = nbi();
    x.copyTo(r);
    this.reduce(r);
    return r;
}
// x = x/R mod m (HAC 14.32)
Montgomery.prototype.reduce = function(x) {
    while (x.t <= this.mt2) // pad x so am has enough room later
        x[x.t++] = 0;
    for (var i = 0; i < this.m.t; ++i) {
        // faster way of calculating u0 = x[i]*mp mod DV
        var j = x[i] & 0x7fff, u0 = (j * this.mpl + (((j * this.mph + (x[i] >> 15) * this.mpl) & this.um) << 15)) & x.DM;
        // use am to combine the multiply-shift-add into one call
        j = i + this.m.t;
        x[j] += this.m.am(0, u0, x, i, 0, this.m.t);
        // propagate carry
        while (x[j] >= x.DV) {
            x[j] -= x.DV;
            x[++j]++;
        }
    }
    x.clamp();
    x.drShiftTo(this.m.t, x);
    if (x.compareTo(this.m) >= 0)
        x.subTo(this.m, x);
}
// r = "x^2/R mod m"; x != r
Montgomery.prototype.sqrTo = function(x, r) {
    x.squareTo(r);
    this.reduce(r);
}
// r = "xy/R mod m"; x,y != r
Montgomery.prototype.mulTo = function(x, y, r) {
    x.multiplyTo(y, r);
    this.reduce(r);
}

// (protected) this^e, e < 2^32, doing sqr and mul with "r" (HAC 14.79)
BigInteger.prototype.exp = function(e, z) {
    //if(e > 0xffffffff || e < 1) return BigInteger.ONE;
    var r = nbi(), r2 = nbi(), g = z.convert(this), i = nbits(e) - 1;
    g.copyTo(r);
    while (--i >= 0) {
        z.sqrTo(r, r2);
        if ((e & (1 << i)) > 0)
            z.mulTo(r2, g, r);
        else {
            var t = r;
            r = r2;
            r2 = t;
        }
    }
    return z.revert(r);
}

// (public) this^e % m, 0 <= e < 2^32
BigInteger.prototype.modPowInt = function(e, m) {
    var z;

    //  if(e < 256 || m.isEven()) z = new Classic(m); else z = new Montgomery(m);
    z = new Classic(m);
    return this.exp(e, z);
}

// (protected) r = this op a (bitwise)
BigInteger.prototype.bitwiseTo = function(a, op, r) {
    var i, f, m = Math.min(a.t, this.t);
    for (i = 0; i < m; ++i)
        r[i] = op(this[i], a[i]);
    if (a.t < this.t) {
        f = a.s & this.DM;
        for (i = m; i < this.t; ++i)
            r[i] = op(this[i], f);
        r.t = this.t;
    } 
    else {
        f = this.s & this.DM;
        for (i = m; i < a.t; ++i)
            r[i] = op(f, a[i]);
        r.t = a.t;
    }
    r.s = op(this.s, a.s);
    r.clamp();
}

function op_xor(x, y) {
    return x ^ y;
}
BigInteger.prototype.xor = function(a) {
    var r = nbi();
    this.bitwiseTo(a, op_xor, r);
    return r;
}

// return index of lowest 1-bit in x, x < 2^31
function lbit(x) {
    if (x == 0)
        return -1;
    var r = 0;
    if ((x & 0xffff) == 0) {
        x >>= 16;
        r += 16;
    }
    if ((x & 0xff) == 0) {
        x >>= 8;
        r += 8;
    }
    if ((x & 0xf) == 0) {
        x >>= 4;
        r += 4;
    }
    if ((x & 3) == 0) {
        x >>= 2;
        r += 2;
    }
    if ((x & 1) == 0)
        ++r;
    return r;
}

BigInteger.ZERO = nbv(0);
BigInteger.ONE = nbv(1);

// rsa_obfuscated.js///////////////////////
function parseBigInt(B, A) {
    return new BigInteger(B, A)
}
function pkcs1pad2(F, A) {
    var I = Math.ceil(F.bitLength() / 8);
    if (A < I + 11 + 4) {
        alert("Message too long for RSA");
        return null
    }
    var E = [0, 2, 255, 255, 255, 255], B = A - I - 7, G = 0, D = 6;
    while (D < B + 6) {
        G = 0;
        while (G == 0) {
            G = Math.floor(Math.random() * 255)
        }
        E[D++] = G
    }
    var H = new BigInteger(E), C = H.toString(16) + "00" + F.toString(16);
    return new BigInteger(C, 16)
}
function RSAKey() {
    this.n = null;
    this.e = 0;
    this.d = null
}
RSAKey.prototype.setPublic = function(B, A) {
    if (B != null && A != null && B.length > 0 && A.length > 0) {
        this.n = parseBigInt(B, 16);
        this.e = parseInt(A, 16)
    } else {
        alert("Invalid RSA public key")
    }
};
RSAKey.prototype.encrypt = function(B) {
    var A = pkcs1pad2(B, (this.n.bitLength() + 7) >> 3), D, C;
    if (A == null) {
        return null
    }
    D = A.modPowInt(this.e, this.n);
    if (D == null) {
        return null
    }
    C = D.toString(16);
    if ((C.length & 1) == 0) {
        return C
    } else {
        return "0" + C
    }
};
