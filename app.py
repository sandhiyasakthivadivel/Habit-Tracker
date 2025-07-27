import streamlit as st
import pandas as pd
from datetime import date
import hashlib  # ✅ for password hashing

st.set_page_config(page_title="Habit Tracker", layout="centered")

users_file = "users.csv"
habits_file = "habits.csv"

# 🔐 Password hashing function
def hash_password(password):
    return hashlib.sha256(password.encode()).hexdigest()

# Load/Create users.csv
try:
    users_df = pd.read_csv(users_file)
except FileNotFoundError:
    users_df = pd.DataFrame(columns=["Username", "Password"])
    users_df.to_csv(users_file, index=False)

# Load/Create habits.csv
try:
    habits_df = pd.read_csv(habits_file)
except FileNotFoundError:
    habits_df = pd.DataFrame(columns=["Username", "Date", "Habit", "Status"])
    habits_df.to_csv(habits_file, index=False)

if "logged_in" not in st.session_state:
    st.session_state.logged_in = False
    st.session_state.username = ""

# Signup
def signup():
    st.subheader("🆕 Sign Up")
    new_user = st.text_input("New Username")
    new_pass = st.text_input("New Password", type="password")
    if st.button("Create Account"):
        if new_user in users_df["Username"].values:
            st.warning("🚫 Username already exists!")
        else:
            # ✅ Store hashed password
            new_data = pd.DataFrame([[new_user, hash_password(new_pass)]], columns=["Username", "Password"])
            new_data.to_csv(users_file, mode="a", header=False, index=False)
            st.success("✅ Account created! Please log in.")

# Login
def login():
    st.subheader("🔐 Login")
    user = st.text_input("Username")
    pw = st.text_input("Password", type="password")
    if st.button("Login"):
        hashed_pw = hash_password(pw)  # ✅ compare with hashed
        if ((users_df["Username"] == user) & (users_df["Password"] == hashed_pw)).any():
            st.session_state.logged_in = True
            st.session_state.username = user
            st.success(f"✅ Welcome {user}!")
        else:
            st.error("❌ Incorrect username or password")

# Habit Tracker Page
def habit_tracker(username):
    st.title(f"🌱 Habit Tracker - Welcome {username}")

    with st.form("habit_form"):
        st.subheader("➕ Add New Habit")
        habit = st.text_input("Enter Habit:")
        status = st.selectbox("Status", ["Done", "Pending", "Skipped", "Nil"], index=0)  # ✅ Default to "Done"
        submitted = st.form_submit_button("Add Habit")
        if submitted and habit.strip() != "":
            new_row = pd.DataFrame([[username, date.today(), habit, status]],
                                   columns=["Username", "Date", "Habit", "Status"])
            new_row.to_csv(habits_file, mode='a', header=False, index=False)
            st.success("✅ Habit Added!")

    st.subheader("📋 Your Habit History")
    all_data = pd.read_csv(habits_file)
    user_data = all_data[all_data["Username"] == username].reset_index(drop=True)

    for i, row in user_data.iterrows():
        col1, col2, col3, col4 = st.columns([2, 3, 3, 2])
        col1.write(row["Date"])
        col2.write(row["Habit"])
        new_status = col3.selectbox("Update Status", ["Nil", "Done", "Pending", "Skipped"], 
                                    index=["Nil", "Done", "Pending", "Skipped"].index(row["Status"]), 
                                    key=f"status_{i}")
        if col4.button("✅ Update", key=f"update_{i}"):
            all_data_idx = all_data[(all_data["Username"] == username) &
                                    (all_data["Date"] == row["Date"]) &
                                    (all_data["Habit"] == row["Habit"])].index
            if not all_data_idx.empty:
                all_data.loc[all_data_idx[0], "Status"] = new_status
                all_data.to_csv(habits_file, index=False)
                st.success(f"✅ '{row['Habit']}' status updated to {new_status}")
                st.rerun()

    st.subheader("🔍 Filter by Date")
    selected_date = st.date_input("Select date", value=date.today())

    # ✅ Fix date filtering with proper type casting
    user_data["Date"] = pd.to_datetime(user_data["Date"]).dt.date
    filtered = user_data[user_data["Date"] == selected_date]

    st.write(f"Habits on {selected_date}:")
    st.dataframe(filtered, use_container_width=True)

# Main App
st.title("🔑 Habit Tracker App")

if not st.session_state.logged_in:
    option = st.radio("Select", ["Login", "Sign Up"])
    if option == "Login":
        login()
    else:
        signup()
else:
    habit_tracker(st.session_state.username)
