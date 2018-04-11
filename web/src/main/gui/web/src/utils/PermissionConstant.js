/**
 * Created by mosesc on 03/30/18.
 */

export function getPermission(permissionName){
  const permissions = {
    // role
    ROLE_ADD: 'ROLE_ADD',
    ROLE_EDIT: 'ROLE_UPDATE',
    ROLE_DELETE: 'ROLE_DELETE',
    // department
    DEPARTMENT_ADD: 'DEPARTMENT_ADD',
    DEPARTMENT_EDIT: 'DEPARTMENT_UPDATE',
    DEPARTMENT_DELETE: 'DEPARTMENT_DELETE',
    // user
    USER_DEPARTMENT_VIEW: 'USER_DEPARTMENT_VIEW',
    USER_ADD: 'USER_ADD',
    USER_UPDATE: 'USER_UPDATE',
    USER_DELETE: 'USER_DELETE',
    ROLE_ASSIGN:'ROLE_ASSIGN',
  };

  return permissions[permissionName];
}
