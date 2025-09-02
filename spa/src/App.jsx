import {useState} from "react";

function App() {
    const [data, setData] = useState();

    const fetchUsers = () => {
        console.log("Getting users");
        fetch('http://localhost:8080/api/users', {
            method: 'GET',
            credentials: 'include',             // if you use cookies/session
            headers: { 'Accept': 'application/json' }
        })
            .then(response => {
                console.log("RESPONSE:", response);
                return response.json()
            })
            .then(users => {
                console.log("Got users: ", users);
                setData(users);
            })
            .catch(error => {
                console.error("A problem happened with fetching users: ", error)
            });
    };

    const logout = () => {
        const formLogout = document.getElementById("logoutForm");
        formLogout.submit();
    }

    return (
        <>
            <h1>Vite + React</h1>
            <div>
                <button type="button" onClick={fetchUsers}>Retrieve users</button>
                <button type="button" onClick={logout}>Logout</button>
                <pre>
                    {JSON.stringify(data, null, 3)}
                </pre>
                <form id="logoutForm" method="GET" action="http://localhost:8080/closesession"/>
            </div>
        </>
    )
}

export default App
