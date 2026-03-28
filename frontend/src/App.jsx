import backgroundImg from "./assets/pixel-background.png"
import { useState, useEffect, useRef } from "react";
import { getBalance, claimReward, withdraw, getTransactions, login, register, getInventory } from "./api";
import coinSound from "./assets/coin-sound.mp3"
import purchaseSound from "./assets/purchase-sound.mp3"
import pixelcoin from "./assets/pixel-coin.png"

function App() {

  const [balance, setBalance] = useState(0)
  const [transactions, setTransactions] = useState([]);
  const [loggedIn, setLoggedIn] = useState(false);
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const [cooldown, setCooldown] = useState(0)
  const [message, setMessage] = useState("")
  const [userEmail, setUserEmail] = useState("")
  const [inventory, setInventory] = useState([])

  const coinAudio = useRef(new Audio(coinSound))
  const purchaseAudio = useRef(new Audio(purchaseSound))

  function showMessage(text){
    setMessage(text)

    setTimeout(() => {
      setMessage("")
    }, 3000)
  }

  async function loadBalance() {
    const data = await getBalance();
    setBalance(data);
  }

  async function loadTransactions(){
    const data = await getTransactions();
    setTransactions(data);
  }

  async function loadInventory(){
    const data = await getInventory();
    setInventory(data);
  }

  useEffect(() => {

    const token = localStorage.getItem("token")
    const savedEmail = localStorage.getItem("email")

    if(token){
      setLoggedIn(true)
      setUserEmail(savedEmail)

      loadBalance()
      loadTransactions()
      loadInventory()
    }

  }, []);

  useEffect(() => {

    if(cooldown <= 0) return

    const timer = setInterval(() => {
      setCooldown((prev) => prev - 1)
    }, 1000)

    return () => clearInterval(timer)

  }, [cooldown]);

  async function handleReward(){
    if(!loggedIn) return

    if(cooldown > 0) return

    try{
      const data = await claimReward()

      coinAudio.current.play()

      setBalance(data.balance)
      await loadTransactions()

      showMessage(`🎉 + ${data.earned} COIN kazandın!`)

      setCooldown(5)
    } catch(e){
      showMessage(e.message)
      return
    }
  }

  async function buyItem(price, itemName){
      try{
      await withdraw(price, itemName);

      purchaseAudio.current.play()

      await loadBalance();
      await loadTransactions();
      await loadInventory();
    } catch(e){
      showMessage(e.message)
    }
  }

  function logout(){
    localStorage.removeItem("token");
    localStorage.removeItem("email");

    setLoggedIn(false);
    setBalance(0)
    setTransactions([])
    setUserEmail("")
  }

  async function handleLogin(){
    try{
      await login(email, password)

      localStorage.setItem("email", email)

      setLoggedIn(true)
      setUserEmail(email)

      showMessage("Login successful ✅")

      await loadBalance()
      await loadTransactions()
    } catch{
      alert("Login failed")
    }
  }

  async function handleRegister(){
    try{
    await register(email, password)
    showMessage("Register successful 🎉")

    localStorage.setItem("email", email)

    await login(email, password)
    setLoggedIn(true)

    setUserEmail(email)

    await loadBalance()
    await loadTransactions()
  } catch(e){
    console.log(e)
    showMessage(e.message)
  }}

  return (
    <>
      <div
        className="min-h-screen flex flex-col items-center bg-center bg-no-repeat bg-[length:100%_100%] pt-24"
        style={{ backgroundImage: `url(${backgroundImg})`}}
      >

        {message && (
          <div className="absolute top-24 bg-green-500 text-white px-6 py-2 rounded-xl shadow-lg animate-bounce">
            {message}
          </div>
        )}

        <div className="fixed top-6 flex justify-center w-full">

        <div className="flex items-center gap-3 bg-white/30 backdrop-blur-md rounded-xl px-4 py-2">

        <span className="text-white font-semibold mr-3">
          Pixel Wallet
        </span>

        {!loggedIn && (
          <>
            <input
              placeholder="email"
              className="px-2 py-1 rounded text-black"
              onChange={(e)=>setEmail(e.target.value)}
            />

            <input
              type="password"
              placeholder="password"
              className="px-2 py-1 rounded text-black"
              onChange={(e)=>setPassword(e.target.value)}
            />

            <button
              onClick={handleLogin}
              className="bg-green-500 px-3 py-1 rounded text-white"
            >
              Login
            </button>

            <button
              onClick={handleRegister}
              className="bg-blue-500 px-3 py-1 rounded text-white"
            >
              Register
            </button>
        </>
      )}

      {loggedIn && (
        <>
        <div className="text-white text-sm mr-2">
          {userEmail}
        </div>

        <button
          onClick={logout}
          className="bg-red-500 px-3 py-1 rounded text-white"
        >
          Logout
        </button>
        </>
      )}

        </div>

    </div>

      <div className="w-[420px] mt-10 rounded-3xl bg-white/20 backdrop-blur-lg border border-white/30 shadow-xl p-8">
          
        <h1 className="text-center text-2xl mb-6 text-white">
          Digital Wallet
        </h1>

        <div className="mb-2 text-white text-lg text-center">
          Balance
        </div>

        <div className="text-orange-400 text-3xl font-bold text-center">
          {balance} COIN
        </div>

        <div className="text-center text-white">
          <button
          onClick={handleReward}
          disabled={cooldown > 0 || !loggedIn}
          className={`text-7xl transition ${
            cooldown > 0
              ? "opacity-50 cursor-not-allowed"
              : "hover:scale-110"
          }`}
          >
          <img className={"w-40"}  src={pixelcoin} alt="coin" />
          </button>

          {cooldown > 0 && (
            <div className="text-white text-sm mt-2">
              Wait {cooldown}s
            </div>
          )}

          <div className="mt-8 flex gap-3 justify-center">

          <button
            onClick={() => buyItem(3), "Apple"}
            disabled={!loggedIn}
            className="flex flex-col items-center bg-white/20 p-4 rounded-xl hover:bg-white/30 hover:scale-105 transition"
            >
            <div className="text-4xl mb-2">🍎</div>
            <div className="text-white text-sm">Apple</div>
            <div className="text-orange-400 text-base text-center mt-2">3 COIN</div>
          </button>

          <button
            onClick={() => buyItem(10), "Burger"}
            disabled={!loggedIn}
            className="flex flex-col items-center bg-white/20 p-4 rounded-xl hover:bg-white/30 hover:scale-105 transition"
            >
            <div className="text-4xl mb-2">🍔</div>
            <div className="text-white text-sm">Burger</div>
            <div className="text-orange-400 text-base text-center mt-2">10 COIN</div>
          </button>

          <button
            onClick={() => buyItem(20), "Pizza"}
            disabled={!loggedIn}
            className="flex flex-col items-center bg-white/20 p-4 rounded-xl hover:bg-white/30 hover:scale-105 transition"
            >
            <div className="text-4xl mb-2">🍕</div>
            <div className="text-white text-sm">Pizza</div>
            <div className="text-orange-400 text-base text-center mt-2">20 COIN</div>
          </button>

          </div>
        </div>
        </div>
          <div className="mt-6 w-full">

          <div className="text-white text-sm mb-2 text-center">
            Transactions
          </div>

          <div className="max-h-32 w-[35%] mx-auto overflow-y-auto text-xs text-white space-y-1">

            {transactions.map((t) => (
              <div key={t.createdAt + t.amount} className="flex justify-between bg-white/10 px-2 py-1 rounded">

                <span>{t.type === "WITHDRAW" ? "PURCHASED": "EARNED"}</span>
                <span className= {`flex items-center gap-1 ${t.type === "WITHDRAW" ? "text-red-400" : "text-green-400"}`}>
                  {t.amount} <img className={"w-4"}  src={pixelcoin} alt="coin" />
                </span>

              </div>
            ))}

            {loggedIn && (
              <div className="fixed left-6 top-1/2 -translate-y-1/2 bg-white/20 backdrop-blur-lg border border-white/30 rounded-2xl p-4 w-36">
                <div className="text-white text-sm text-center mb-3">🎒 Inventory</div>
                {inventory.length === 0 ? (
                  <div className="text-white/50 text-xs text-center">Empty</div>
                ) : (
                  <div className="flex flex-col gap-2">
                    {inventory.map((item) => (
                      <div key={item.id} className="flex items-center justify-between bg-white/10 px-2 py-1 rounded-lg">
                        <span className="text-lg">
                          {item.itemName === "Apple" ? "🍎" : item.itemName === "Burger" ? "🍔" : "🍕"}
                        </span>
                        <span className="text-white text-xs">{item.itemName}</span>
                        <span className="text-orange-400 text-xs">x{item.quantity}</span>
                      </div>
                    ))}
                  </div>
                )}
              </div>
            )}

          </div>
        </div>
      </div>
    </>
  )
}

export default App
