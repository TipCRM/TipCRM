/**
 * Created by mosesc on 04/13/18.
 */

export function getAuthority(){
  return localStorage.getItem('tip_user_login');
}

export function setAuthority(authority){
  return localStorage.setItem('tip_user_login', authority);
}
