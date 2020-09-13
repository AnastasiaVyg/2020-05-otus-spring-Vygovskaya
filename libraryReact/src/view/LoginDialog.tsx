import React, {Dispatch, Ref, useRef} from "react";
import {Button} from "@material-ui/core";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import Dialog from "@material-ui/core/Dialog";
import TextField from "@material-ui/core/TextField";
import DialogActions from "@material-ui/core/DialogActions";
import {Book} from "../model/Book";
import {addComment, FetchProps, loginFetch} from "../store/Actions";

export interface LoginDialogProps {
    dispatch: Dispatch<any>
    fetchProps: FetchProps
}

export default function LoginDialog(props: LoginDialogProps) {
    const [open, setOpen] = React.useState(true);
    // setOpen(true);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const inputLogin: React.RefObject<HTMLInputElement> = React.createRef()
    const inputPassword: React.RefObject<HTMLInputElement> = React.createRef()

    const handleLogin = () => {
        const login: string = inputLogin.current!.value
        const password: string = inputPassword.current!.value
        loginFetch(props.dispatch, login, password, props.fetchProps)
        setOpen(false);
    }

    return (
        <div>
            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                Authentication form
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="4545authentication-form">
                <DialogTitle id="authentication-form">Authentication form</DialogTitle>
                <DialogContent>
                    <TextField
                        inputRef={inputLogin}
                        autoFocus
                        margin="dense"
                        id="text"
                        label="Login"
                        type="text"
                        fullWidth
                    />
                    <TextField
                        inputRef={inputPassword}
                        autoFocus
                        margin="dense"
                        id="password"
                        label="Password"
                        type="password"
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleLogin} color="primary">
                        Ok
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}