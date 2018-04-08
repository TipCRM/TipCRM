/**
 * Created by mosesc on 03/30/18.
 */

export function getPermission(permissionName){
  const permissions = {
    ROLE_ADD: 'ROLE_ADD',
    ROLE_EDIT: 'ROLE_UPDATE',
    ROLE_DELETE: 'ROLE_DELETE',
    DEPARTMENT_ADD: 'DEPARTMENT_ADD',
    DEPARTMENT_EDIT: 'DEPARTMENT_UPDATE',
    DEPARTMENT_DELETE: 'DEPARTMENT_DELETE',
  };

  return permissions[permissionName];
}
