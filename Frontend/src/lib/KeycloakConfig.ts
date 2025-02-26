import Keycloak from 'keycloak-js';

const keycloak = new Keycloak({
    url: 'http://localhost:9000/',
    realm: 'LibraryServiceRealm',
    clientId: 'LibraryServicePKCE',
});



export default keycloak;

