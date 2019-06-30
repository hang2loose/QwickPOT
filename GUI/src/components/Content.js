import React from 'react';
import ChatWindow from './ChatWindow';
import InputBar from './InputBar';
import Statistic from './Statistic';
import {withStyles} from '@material-ui/core/styles';

const styles = theme => ({
    content: {
        flexGrow: 1,
        padding: theme.spacing.unit * 3,
        height: '100vh',
        overflow: 'auto',
    },
});

class Content extends React.Component {

    render() {

        const { classes } = this.props;

        switch (this.props.viewComponent) {
            case 'chat':
                return (
                    <main className={classes.content} >
                        <ChatWindow messages = {this.props.messages} />
                        <InputBar onSend={this.props.sendHandler}
                                  toBottom={this.props.forceToBottom}
                        />
                    </main>
                );
            case 'statistic':
                return (
                    <main className={classes.content} >
                        <Statistic department={this.props.department}/>
                    </main>
                );
        }
    }
}

export default withStyles(styles)(Content);