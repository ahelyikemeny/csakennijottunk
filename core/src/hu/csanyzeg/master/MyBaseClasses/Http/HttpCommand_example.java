/*

        httpCommand = new HttpCommand("http://193.224.143.135:9999"){
            @Override
            protected void failed(HttpErrors httpErrors) {

            }

            @Override
            protected void responsed() {

                HashMap<String, String> hm = getReceive();
                int m = Integer.valueOf(getReceive().get("message"));
                switch (m) {
                    case 60:
                        nextScreen = 60;
                        break;
                    ....
                }

            }
        };


        httpCommand.getSend().put("user",pref_user_pw.getString("user"));
        httpCommand.getSend().put("password",pref_user_pw.getString("password"));
        httpCommand.getSend().put("message", String.valueOf(MessageTypes.HELLO.value));


        httpCommand.sendCommand();
    }
*/