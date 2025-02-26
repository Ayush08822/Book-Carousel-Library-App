
import { useEffect } from 'react';
import { useKeycloak } from '@react-keycloak/web';

export const KeycloakLogin = () => {
    const { keycloak } = useKeycloak();

    useEffect(() => {
        if (keycloak) {
            keycloak.login({
                    redirectUri: 'https://localhost:5173',  // The URI where the user will be redirected after successfull login
            });
        }
    }, [keycloak]);

    return <div></div>;
}
