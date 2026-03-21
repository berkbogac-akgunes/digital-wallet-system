const API = import.meta.env.VITE_API_URL;

export function getToken() {
  return localStorage.getItem("token")
}

export function authHeaders() {
  return {
    "Content-Type": "application/json",
    "Authorization": "Bearer " + getToken()
  }
}

export async function login(email, password) {

  const res = await fetch(API + "/auth/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({ email, password })
  })

  if(!res.ok){
    const error = await res.json()
    throw new Error(error.message)
  }

  const data = await res.json()

  localStorage.setItem("token", data.token)

  return data
}

export async function getBalance() {

  const res = await fetch(API + "/wallet/balance", {
    headers: authHeaders()
  })

  return res.json()
}

export async function claimReward() {

  const res = await fetch(API + "/wallet/reward", {
    method: "POST",
    headers: authHeaders()
  })

  return res.json()
}

export async function withdraw(amount) {

  await fetch(API + "/wallet/withdraw", {
    method: "POST",
    headers: authHeaders(),
    body: JSON.stringify({ amount })
  })
}

export async function getTransactions() {

  const res = await fetch(API + "/wallet/transactions", {
    headers: authHeaders()
  })

  return res.json()
}

export async function register(email, password){

  const res = await fetch(API + "/auth/register",{
    method:"POST",
    headers:{
      "Content-Type":"application/json"
    },
    body: JSON.stringify({email, password})
  })

  if(!res.ok){
    const error = await res.json()
    throw new Error(error.message)
  }

}