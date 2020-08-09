import React, {Dispatch, useRef} from "react";
import {Button} from "@material-ui/core";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import Dialog from "@material-ui/core/Dialog";
import TextField from "@material-ui/core/TextField";
import DialogActions from "@material-ui/core/DialogActions";
import {Book} from "../model/Book";
import {addComment} from "../store/Actions";

export interface CommentDialogProps {
    book: Book
    dispatch: Dispatch<any>
}

export default function AddCommentDialog(props : CommentDialogProps) {
    const [open, setOpen] = React.useState(false);

    const handleClickOpen = () => {
        setOpen(true);
    };

    const handleClose = () => {
        setOpen(false);
    };

    const inputText: React.RefObject<HTMLInputElement> = React.createRef()

    const handleAddComment = () => {
        const comment: string = inputText.current!.value
        addComment(props.dispatch, props.book.id, comment)
        setOpen(false);
    }

    return (
        <div>
            <Button variant="contained" color="primary" onClick={handleClickOpen}>
                Add comment
            </Button>
            <Dialog open={open} onClose={handleClose} aria-labelledby="add-comment-dialog">
                <DialogTitle id="add-comment-dialog">Add comment</DialogTitle>
                <DialogContent>
                    <TextField
                        inputRef={inputText}
                        autoFocus
                        margin="dense"
                        id="text"
                        label="Comment"
                        type="text"
                        fullWidth
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose} color="primary">
                        Cancel
                    </Button>
                    <Button onClick={handleAddComment} color="primary">
                        Ok
                    </Button>
                </DialogActions>
            </Dialog>
        </div>
    );
}