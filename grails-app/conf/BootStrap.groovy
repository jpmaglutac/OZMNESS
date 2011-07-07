import OZMNESS.*

class BootStrap {

    def springSecurityService

    def init = { servletContext ->
        def roles = [:]
        def users = [:]
        
        roles.admin = Role.findByAuthority("ROLE_ADMIN") ?: \
            new Role(authority: "ROLE_ADMIN").save()
            
        users.admin = User.findByUsername('admin') ?: \
            new User(username: 'admin', \
            password: springSecurityService.encodePassword('password'), \
            enabled: true, accountExpired: false, \
            accountLocked: false, passwordExpired: false).save()
            
        UserRole.create( users.admin, roles.admin )
    }
    def destroy = {
    }
}
