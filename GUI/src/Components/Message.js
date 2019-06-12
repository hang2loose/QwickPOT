import React from 'react';
import { withStyles } from '@material-ui/core/styles';
import Card from "@material-ui/core/Card";
import CardContent from "@material-ui/core/CardContent";
import Typography from "@material-ui/core/Typography";

const styles = theme => ({
    card: {
        margin: theme.spacing.unit,
    },
    content: {
        padding: theme.spacing.unit,
    },
    title: {
        fontSize: 14,
    },
});

class Message extends React.Component {
    render() {

        const { classes } = this.props;

        return (
            <Card className={classes.card}>
                <CardContent className={classes.content}>
                    <Typography className={classes.title} color="textSecondary" gutterBottom>
                        {this.props.username}
                    </Typography>
                    <Typography>
                        { this.props.message }
                    </Typography>
                </CardContent>
            </Card>
        );
    }
}

Message.defaultProps = {
    message: '',
    username: '',
};

export default withStyles(styles)(Message);