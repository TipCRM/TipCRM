/**
 * Created by mosesc on 03/30/18.
 */

export function getPermission(permissionName){
  const permissions = {
    ROLE_ADD: 'role:add',
    ROLE_EDIT: 'role:update',
    ROLE_DELETE: 'role:delete'
  };

  return permissions[permissionName];
}
